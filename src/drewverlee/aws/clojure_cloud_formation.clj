(ns drewverlee.aws.clojure-cloud-formation
  (:require
   [hickory.core :as h]
   [clojure.string :as str]
   [clojure.data.json :as json]
   [camel-snake-kebab.core :as csk]))

(defn aws-resource-string->aws-resource-keyword
  [aws-resource-string]
  (let [ss (map csk/->kebab-case (str/split aws-resource-string #"::|\."))]
    (keyword
     (str
      (str/join "."  (butlast ss))
      "/"
      (last ss)))))

(def cloud-formation-resource-specification
  (-> "CloudFormationResourceSpecification.json"
      slurp
      (json/read-str :key-fn (fn [k] (if-not (str/includes? k "::")
                                       (keyword k)
                                       (aws-resource-string->aws-resource-keyword k))))))

(comment

  (->> cloud-formation-resource-specification
       :ResourceTypes
       :aws.iam/role
       :Properties
       keys
       )
  ;; => (:ManagedPolicyArns
  ;;     :PermissionsBoundary
  ;;     :Path
  ;;     :Policies
  ;;     :Tags
  ;;     :RoleName
  ;;     :MaxSessionDuration
  ;;     :Description
  ;;     :AssumeRolePolicyDocument)


  (->> cloud-formation-resource-specification
       :ResourceTypes
       :aws.iam/role
       :Properties
       :RoleName)

  ;; => {:Documentation
  ;;     "http://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-resource-iam-role.html#cfn-iam-role-rolename",
  ;;     :PrimitiveType "String",
  ;;     :Required false,
  ;;     :UpdateType "Immutable"}



  nil)
