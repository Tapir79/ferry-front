(ns ferry-front.events.booking-status
  (:require
    [re-frame.core :as rf]
    [ferry-front.db :as db]))

(rf/reg-event-db
  ::ws-booking-status-received
  (fn [db [_ new-booking]]
    (assoc db :test-new-booking new-booking)))
