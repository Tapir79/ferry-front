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
