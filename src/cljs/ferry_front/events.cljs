(ns ferry-front.events
  (:require
   [re-frame.core :as re-frame]
   ;[day8.re-frame.http-fx]
   [ferry-front.db :as db]))

(enable-console-print!)

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

;;;; Test events

(re-frame/reg-event-db
  ::add-to-new-test
  (fn [db [_ [key-path value]]]
    (assoc-in db (flatten [:new-booking key-path]) value)))



;;;; Booking events

(re-frame/reg-event-fx
  ::send-new-booking
  (fn [_ _]
    (println "new booking sent")))



(re-frame/reg-event-db
  ::add-to-new-booking
  (fn [db [_ [key-path value]]]
    (assoc-in db (flatten [:new-booking key-path]) value)))

(re-frame/reg-event-db
  ::change-line
  (fn [db [_ value]]
    (assoc db :line value)))

(re-frame/reg-event-db
  ::change-line-to
  (fn [db [_ value]]
    (assoc db :line-to value)))

(re-frame/reg-event-db
  ::null-lines
  (fn [db [_ _]]
    (-> db
        (assoc :line-to nil)
        (assoc :line-from nil))))

(re-frame/reg-event-fx
  ::change-line-booking
  (fn [{:keys [db]} [_ line]]
    {:dispatch-n (list [::change-line line]
                       [::null-lines]
                       [::add-to-new-booking [:line-to nil]]
                       [::add-to-new-booking [:line-from nil]]
                       [::add-to-new-booking [:line line]])
     :db db}))


