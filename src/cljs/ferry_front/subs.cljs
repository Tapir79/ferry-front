(ns ferry-front.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

;;; Routes subs

(re-frame/reg-sub
  ::stop-routes
  (fn [db]
    (:stop-routes db)))

(re-frame/reg-sub
  ::chosen-line-geom
  (fn [db]
    (:chosen-line-geom db)))

;;;; Test subs

(re-frame/reg-sub
  ::test
  (fn [db]
    (:test db)))

(re-frame/reg-sub
  ::tests
  (fn [db]
    (:tests db)))


