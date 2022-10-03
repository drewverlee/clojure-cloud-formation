# Clojure Cloud Formation

A work in progress towards creating a clojure cdk for aws cloud formation.

# What it is

It helps you get documentation on cloud formation resources:

```clojure
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
```


# What it wants to be

A"CDK".

* specification on resources malli or clojure spec
* functions like:

```clojure
(aws.iam/role {:role-name "hi"})
```

know with clj-kondo on this kind of thing in your editor:

```clojure
(aws.iam/role {:role-name ["hi"]})
                           ^--> role-name should be string.
```

this will probably output cloudformation json..

```clojure
(aws.iam/role {...})
```

like this:

```json
{
  "Type" : "AWS::IAM::Role",
  "Properties" : {
      "AssumeRolePolicyDocument" : Json,
      "Description" : String,
      "ManagedPolicyArns" : [ String, ... ],
      "MaxSessionDuration" : Integer,
      "Path" : String,
      "PermissionsBoundary" : String,
      "Policies" : [ Policy, ... ],
      "RoleName" : String,
      "Tags" : [ Tag, ... ]
    }
}

```

also searching would be cool

# How you can help

idk yet! tell me how you can help!
