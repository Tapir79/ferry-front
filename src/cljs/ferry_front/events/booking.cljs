(ns ferry-front.events.booking
  (:require
    [re-frame.core :as rf]
    [day8.re-frame.http-fx]
    [ferry-front.db :as db]
    [ajax.core :as ajax]
    [ferry-front.events.api_errors :as ae]))

(enable-console-print!)

(defn handle-change-line
  [db [_ line]]
   (assoc db :booking-line line))

(rf/reg-event-db ::change-line handle-change-line)

(defn handle-change-departure-stop
  [db [_ stop]]
   (assoc db :booking-departure-stop stop))

(rf/reg-event-db ::change-departure-stop handle-change-departure-stop)

(defn handle-change-arrival-stop
  [db [_ stop]]
   (assoc db :booking-arrival-stop stop))

(rf/reg-event-db ::change-destination-stop handle-change-arrival-stop)

(rf/reg-event-db
  ::change-stop-routes
  (fn [db [_ res]]
    (assoc db :stop-routes res)))

(defn handle-search-click
  [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/stoproutes")
                  :timeout 50000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-stop-routes]
                  :on-failure [::ae/http-request-failed]}})

(rf/reg-event-fx ::search-click handle-search-click)

(rf/reg-event-db
  ::select-route
  (fn [db [_ route]]
    (assoc db :booking-selected-route route)))

(rf/reg-event-fx
  ::book-trip
  (fn [{:keys [db]} _]
    (println "selectedtrip" (:booking-selected-route db))
    {:db         db
     :http-xhrio {:method          :post
                  :uri             (str "http://localhost:8080/bookings")
                  :params          (:booking-selected-route db)
                  :timeout         8000
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::update-test-state]
                  :on-failure      [::http-request-failed]}}))

(rf/reg-event-db
  ::update-booking-status-count
  (fn [db [_ status-count]]
    (assoc db :booking-status-count status-count)))


(rf/reg-event-fx
  ::get-booking-status-count
  (fn [{:keys [db]} _]
    {:db         db
     :http-xhrio {:method          :get
                  :uri             (str "http://localhost:8080/booking-status-count")
                  :timeout         8000
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::update-booking-status-count]
                  :on-failure      [::ae/http-request-failed]}}))
