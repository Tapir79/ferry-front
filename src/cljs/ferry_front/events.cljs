(ns ferry-front.events
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.db :as db]))

(enable-console-print!)

(re-frame/reg-event-fx
  ::send-new-booking
  (fn [_ _]
    (println "new booking sent")))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
  ::add-to-new-booking
  (fn [db [_ [key-path value]]]
    (assoc-in db (flatten [:new-booking key-path]) value)))
