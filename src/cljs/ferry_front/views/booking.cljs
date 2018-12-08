(ns ferry-front.views.booking
  (:require
    [re-frame.core :as re-frame]
    [stylefy.core :as stylefy]
    [ferry-front.components.booking-form :as booking-form]
    [ferry-front.components.timetables :as timetables]
    [ferry-front.views.map :refer [main-map]]
    [ferry-front.components.loader :refer [compass-loader]]))

(def booking-result-style {:width "97.5%"})

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
        [:div (stylefy/use-style
                booking-result-style                        ;-ml-1 lg:ml-3 sm:mx-1
                {:class "flex justify-center flex-col xl:flex-row sm:w-full ml-1 sm:ml-3 lg:ml-5 sm:mx-1"})
         [:div {:class "flex-1 mb-1 xl:mr-1"}
          [timetables/booking-timetable]]
         [:div {:class "hidden sm:block flex-1 p-2 rounded bg-blue-lighter"}
          [main-map]]]])))


