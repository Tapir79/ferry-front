(ns ferry-front.events.stops
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ferry-front.db :as db]
    [ajax.core :as ajax]
    [ferry-front.events.api_errors :as ae]))

(enable-console-print!)

(re-frame/reg-event-db
  ::change-stops
  (fn [db [_ res]]
    (assoc db :stops res)))

(re-frame/reg-event-fx
  ::get-stops
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method :get
                  :uri (str "http://localhost:8080/stops")
                  :timeout 8000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::change-stops]
                  :on-failure [::ae/http-request-failed]}}))
