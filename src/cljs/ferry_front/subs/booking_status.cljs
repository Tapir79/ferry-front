(ns ferry-front.subs.booking-status
  (:require
    [re-frame.core :as rf]))

(rf/reg-sub
  ::new-booking-status
  (fn [db _]
    (:test-new-booking db)))
