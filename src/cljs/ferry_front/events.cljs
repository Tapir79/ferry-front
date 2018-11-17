(ns ferry-front.events
  (:require
   [re-frame.core :as re-frame]
   [ferry-front.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
  ::add-to-new-booking
  (fn [db [_ [key-path value]]]
    (assoc-in db (flatten [:new-booking key-path]) value)))

#_(re-frame/reg-event-fx
  ::init-state
  (fn [{:keys [db]} [_ _]]
    {:db db
     :dispatch-n (list [::init-timetables])}))