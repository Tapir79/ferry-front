(ns ferry-front.events.events-timetables
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ferry-front.db :as db]
    [ajax.core :as ajax]))

(enable-console-print!)

(re-frame/reg-event-db
  ::http-request-failed
  (fn [db [_ value]]
    (assoc db :http-request-message value)))

(re-frame/reg-event-db
  ::change-stop-routes
  (fn [db [_ res]]
    (assoc db :stop-routes res)))

(re-frame/reg-event-db
  ::change-stops
  (fn [db [_ res]]
    (assoc db :stops res)))

(re-frame/reg-event-db
  ::change-line-segments
  (fn [db [_ res]]
    (assoc db :linesegments res)))

; from 2 to 4
#_(re-frame/reg-event-db
  ::change-line-segment
  (fn [db [_ segment]]
    (assoc db :line-segment segment)))


(re-frame/reg-event-fx
  ::get-stop-routes
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/stoproutes")
                  :timeout 8000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-stop-routes]
                  :on-failure [::http-request-failed]}}))

(re-frame/reg-event-fx
  ::get-stops
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/stops")
                  :timeout 8000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-stops]
                  :on-failure [::http-request-failed]}}))

(re-frame/reg-event-fx
  ::get-line-segments
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/linesegments")
                  :timeout 30000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-line-segments]
                  :on-failure [::http-request-failed]}}))


