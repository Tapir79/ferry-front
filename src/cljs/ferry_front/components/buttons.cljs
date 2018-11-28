(ns ferry-front.components.buttons
  (:require [stylefy.core :refer [use-style]]))

(def default-button-style {:margin-top "0.5em"})

(defn default [label on-click-function]
  [:button
   (use-style default-button-style {:on-click on-click-function})
                      label])
