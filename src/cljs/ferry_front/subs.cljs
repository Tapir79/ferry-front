(ns ferry-front.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

;;;; Test subs

(re-frame/reg-sub
  ::test
  (fn [db]
    (:test db)))

(re-frame/reg-sub
  ::tests
  (fn [db]
    (:tests db)))

;;;;; Booking subs

(re-frame/reg-sub
  ::line
  (fn [db]
    (:line db)))

(re-frame/reg-sub
  ::line-from
  (fn [db]
    (:line-from db)))

(re-frame/reg-sub
  ::line-to
  (fn [db]
    (:line-to db)))

(re-frame/reg-sub
  ::new-booking
  (fn [db]
    (:new-booking db)))
