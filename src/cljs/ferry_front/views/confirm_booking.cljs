(ns ferry-front.views.confirm-booking
  (:require [re-frame.core :as rf]
            [ferry-front.subs.booking :as s-booking]))

(defn main-view []
  (let [route (rf/subscribe [::s-booking/selected-route])]
    [:div {:class "flex flex-col bg-blue-light text-gray-dark rounded"}
      [:div {:class "bg-white rounded"} "Reservation"
       [:div (:ship_name @route)]]
     [:button {:class "bg-blue-darker text-white rounded p-3"} "Send reservation"]]))
