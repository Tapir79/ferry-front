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

;;;; lines subs

(re-frame/reg-sub
  ::lines
  (fn [db]
    (:lines db)))

;;;; stops subs

(re-frame/reg-sub
  ::stops
  (fn [db]
    (:stops db)))


(re-frame/reg-sub
  ::linesegments
  (fn [db]
    (:linesegments db)))

;;;; Test subs

(re-frame/reg-sub
  ::test
  (fn [db]
    (:test db)))

(re-frame/reg-sub
  ::tests
  (fn [db]
    (:tests db)))


