(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
            [ferry-front.events :as events]
            [ferry-front.components.inputs :as inputs]
            [ferry-front.components.buttons :as buttons]
            ))

(defn booking-form []
  [:form
   [inputs/input-field {:content "linje och rutt från"
                        :name "linje från"
                        :on-change-function #(re-frame/dispatch [::events/add-to-new-booking [[:line-from] %]])}]

   [inputs/input-field {:content "linje och rutt till"
                        :name "linje till"
                        :on-change-function #(re-frame/dispatch [::events/add-to-new-booking [[:line-to] %]])}]

   [buttons/default {:on-click-function #(re-frame/dispatch [::events/send-new-booking])}]])
