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

(re-frame/reg-event-fx
  ::get-stop-routes
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/stoproutes")
                  :timeout 8000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-stop-routes]
                  :on-failure [::ae/http-request-failed]}}))
