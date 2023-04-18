# ☁️ kotlin-google-cloud-function

<a href="https://twitter.com/garylaurenceau" target="_blank">
    <img alt="Twitter: @garylaurenceau" src="https://img.shields.io/twitter/follow/garylaurenceau.svg?style=social" />
</a>

## 📘 Two templates to create Google Cloud Function with Kotlin 1.8.0

### 👋 Basic `Hello World`:
```
./gradlew :helloFunction:runFunction
```
Open [http://localhost:8080](http://localhost:8080) to check if it works.

### 🔁 Proxy call with Ktor
 ```
 ./gradlew :proxyFunction:runFunction 
 ```
Open [http://localhost:8080?city=London](http://localhost:8080?city=London) to see weather in London.

## 🚀 Deployment:
Build:
```
./gradlew :helloFunction:buildFunction
```

Deploy function on Google Cloud Platform. Endpoint is `helloWorld`.
```
gcloud functions deploy helloWorld \
    --gen2 \
    --runtime java11 \
    --entry-point com.example.HelloFunction \
    --trigger-http \
    --memory=256MB \
    --source=helloFunction/build/deploy \
    --region=europe-west1 \
    --allow-unauthenticated
```

Navigate to [Google Cloud Function](https://console.cloud.google.com/functions/list), and open your project to list your function.

## 👨‍💻 Google Cloud Platform

You may need to initialize Google Cloud before deploying. To do install gcloud then run:
```
gcloud auth login
gcloud config set project PROJECT_ID
```

More info on the official [cloud function](https://cloud.google.com/functions/docs) and [config](https://cloud.google.com/sdk/gcloud/reference/config/set) documentation.

## ✍️ Author

👤 **GaryLaurenceau**

* Twitter: <a href="https://twitter.com/GaryLaurenceau" target="_blank">@GaryLaurenceau</a>
* Email: gary.laurenceau@gmail.com
* Working on: https://pixel-perfect.dev and https://sumupnow.com

## 📝 License

```
MIT License

Copyright © 2023 - GaryLaurenceau

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```