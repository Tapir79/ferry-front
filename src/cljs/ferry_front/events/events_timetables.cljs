(ns ferry-front.events.events-timetables
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ferry-front.db :as db]
    [ajax.core :as ajax]
    [ferry-front.events.api_errors :as ae]))

(enable-console-print!)

(re-frame/reg-event-db
  ::change-stop-routes
  (fn [db [_ res]]
    (assoc db :stop-routes res)))

(re-frame/reg-event-db
  ::change-stops
  (fn [db [_ res]]
    (assoc db :stops res)))

(re-frame/reg-event-db
  ::change-line
  (fn [db [_ line]]
    (assoc db :line line)))

(re-frame/reg-event-db
  ::change-line-segments
  (fn [db [_ res]]
    (assoc db :linesegments res)))

(re-frame/reg-event-db
  ::change-from
  (fn [db [_ from]]
    (assoc db :from from)))

(re-frame/reg-event-db
  ::change-to
  (fn [db [_ to]]
    (assoc db :to to)))

(re-frame/reg-event-db
  ::change-line-segment
  (fn [db [_ segment]]
    (assoc db :line-segment segment)))


(re-frame/reg-event-db
  ::change-stops
  (fn [db [_ res]]
    (assoc db :stops res)))


(re-frame/reg-event-fx
  ::get-stop-routes
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/stoproutes")
                  :timeout 50000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-stop-routes]
                  :on-failure [::ae/http-request-failed]}}))

(re-frame/reg-event-fx
  ::get-line-segments
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/linesegments")
                  :timeout 50000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-line-segments]
                  :on-failure [::ae/http-request-failed]}}))



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
