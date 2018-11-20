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