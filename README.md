# pay-payments

Prototype microservice for storing information about created payments while they are in-flight.

This microservice is required if we build open banking payments with a payment method selection page as part of the paying user journey. 

[pay-publicapi](https://github.com/alphagov/pay-publicapi) creates payments in this microservice,

[pay-publicapi](https://github.com/alphagov/pay-publicapi) and [pay-frontend](https://github.com/alphagov/pay-frontend) get non-payment method specific information about a payment.

## How to run

Check out the [pay-infra branch](https://github.com/alphagov/pay-infra/tree/new-payment-microservice-prototype) that has configuration for running the new app as part of the local Docker compose cluster.

```sh
pay local up --cluser card --local payments
```

## API Specification

The [API Specification](openapi/payments_spec.yaml) provides more detail on the paths and operations including examples.

[View the API specification for payments in Swagger Editor](https://editor.swagger.io/?url=https://raw.githubusercontent.com/stephencdaly/pay-payments/main/openapi/payments_spec.yaml).

## Licence

[MIT License](LICENSE)

## Vulnerability Disclosure

GOV.UK Pay aims to stay secure for everyone. If you are a security researcher and have discovered a security vulnerability in this code, we appreciate your help in disclosing it to us in a responsible manner. Please refer to our [vulnerability disclosure policy](https://www.gov.uk/help/report-vulnerability) and our [security.txt](https://vdp.cabinetoffice.gov.uk/.well-known/security.txt) file for details.
