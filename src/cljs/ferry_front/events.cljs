(ns ferry-front.events
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ferry-front.db :as db]
    [ajax.core :as ajax]
    [ferry-front.events.events-timetables :as et]))

(enable-console-print!)

(re-frame/reg-event-fx
  ::initialize-timetables
  (fn [{:keys [db]} [_ _]]
    {:dispatch-n (list [::et/get-line-segments]
                       [::et/get-stops]
                       [::et/get-stop-routes])
     :db db}))

(re-frame/reg-event-fx
  ::initialize-db
  (fn [{:keys [db]} [_ _]]
    {:db db/default-db
     :dispatch-n (list [::initialize-timetables])}))

;;;; Test events

(re-frame/reg-event-fx
  ::get-all-tests
  (fn [{:keys [db]} [_ _]]
    {:db         db
     :http-xhrio {:method          :get
                  :uri             (str "http://localhost:8080/tests")
                  :timeout         8000
                  :response-format (ajax/json-response-format {:keywords? true})
                  ;:response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::change-tests]
                  :on-failure      [::http-request-failed]}}))


(re-frame/reg-event-fx
  ::save-test
  (fn [{:keys [db]} _]
    (println "new-test" (:new-test db))
    {:db db
     :http-xhrio {:method :post
                  :uri (str "http://localhost:8080/tests")
                  :params (:new-test db)
                  :timeout 8000
                  :format (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::update-test-state]
                  :on-failure [::http-request-failed]}}))

(re-frame/reg-event-fx
  ::update-test-state
  (fn [{:keys [db]} [_ _]]
    (println "success")
    {:dispatch-n (list [::get-all-tests])
     :db db}))

(re-frame/reg-event-fx
  ::init-tests
  (fn [{:keys [db]} [_ _]]
    {:dispatch-n (list [::get-all-tests])
     :db         db}))

(re-frame/reg-event-db
  ::change-tests
  (fn [db [_ tests]]
    (assoc db :tests tests)))

(re-frame/reg-event-db
  ::http-request-failed
  (fn [db [_ value]]
    (assoc db :http-request-message value)))

(re-frame/reg-event-db
  ::add-to-new-test
  (fn [db [_ [key-path value]]]
    (assoc-in db (flatten [:new-test key-path]) value)))

(re-frame/reg-event-fx
  ::send-new-test
  (fn [_ _]
    (println "new booking sent")))

