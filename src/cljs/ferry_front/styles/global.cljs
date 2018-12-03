(ns ferry-front.styles.global
  (:require [stylefy.core :as stylefy]))

(def ^{:const true} mobile-width "576px")

(defn init-global-styles []
  (stylefy/tag "body" {:font-family "-apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', 'Fira Sans','Droid Sans', 'Helvetica Neue', sans-serif"
                       :color "#454545"
                       :background-color "#2779bd"
                       :line-height 1.4
                       :font-size "16px"}))
