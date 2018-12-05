(ns ferry-front.subs.booking
  (:require
    [re-frame.core :as rf]))

(rf/reg-sub
  ::line
  (fn [db _]
    (get db ::booking-line)))

(rf/reg-sub
  ::departure-stop
  (fn [db _]
  (get db ::booking-departure-stop)))

(rf/reg-sub
  ::destination-stop
  (fn [db _]
  (get db ::booking-destination-stop)))

(rf/reg-sub
  ::stop-routes
  (fn [db]
    (:stop-routes db)))
