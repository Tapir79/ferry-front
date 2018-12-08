(ns ferry-front.views.booking
  (:require
    [re-frame.core :as re-frame]
    [ferry-front.components.booking-form :as booking-form]
    [ferry-front.components.timetables :as timetables]
    [ferry-front.views.map :refer [main-map]]
    [ferry-front.components.loader :refer [compass-loader]]))


(defn main-loader []
  [:div {:class "flex justify-center items-center h-screen w-screen"}
   (compass-loader)])

(defn booking-view
  []
  (let [ready?  (re-frame/subscribe [:initialised?])]
    (if-not @ready?
      (main-loader)
      [:div
       [booking-form/booking-form]
        [:div {:class "flex"}
         [timetables/booking-timetable]
         [:div {:class "hidden sm:block"}
          [main-map]]]])))


