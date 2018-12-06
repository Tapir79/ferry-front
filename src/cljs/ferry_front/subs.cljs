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
  ::booking-line
  (fn [db]
    (:booking-line db)))

(re-frame/reg-sub
  ::lines
  (fn [db]
    (:lines db)))

(re-frame/reg-sub
  ::line
  (fn [db]
    (:line db)))

(re-frame/reg-sub
  ::line-segments
  (fn [db]
    (:linesegments db)))

;;;; stops subs

(re-frame/reg-sub
  ::booking-departure-stop
  (fn [db]
    (:booking-departure-stop db)))

(re-frame/reg-sub
  ::booking-arrival-stop
  (fn [db]
    (:booking-arrival-stop db)))

(re-frame/reg-sub
  ::stops
  (fn [db]
    (:stops db)))

(re-frame/reg-sub
  ::highlight-segment
  (fn [db]
    (:highlight-segment db)))


;;;; Test subs

(re-frame/reg-sub
  ::test
  (fn [db]
    (:test db)))

(re-frame/reg-sub
  ::tests
  (fn [db]
    (:tests db)))





