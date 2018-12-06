(ns ferry-front.views.timetables
  (:require [re-frame.core :as rf]
            [ferry-front.subs.booking :as s-booking]
            [stylefy.core :as stylefy]
            [ferry-front.styles.global :refer [mobile-width]]))

(defn timetable [stop-routes]
  [:div {:class "mb-3 mx-3 bg-blue-lighter p-2 rounded"}
    [:ul {:class "list-reset"}
     (for [route @stop-routes]
       [:li {:key (:segment_id route)
             :class "flex text-gray justify-between rounded bg-white hover:bg-grey-lighter border-b p-3 mb-px cursor-pointer"}
        [:div (:start_time route)]
        [:div (:ship_name route)]
        [:div (:passengers route)]
        [:div (:cars route)]
        [:div (:end_time route)]])]])

(defn booking-timetable []
  (let [stop-routes (rf/subscribe [::s-booking/stop-routes])]
    (when-not (empty? @stop-routes))
      (timetable stop-routes)))
