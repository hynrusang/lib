# js-lib  
A collection of lightweight JavaScript libraries designed to create dynamic web pages and manage data with ease.  
This library is dependency-free, works without any build steps, and can be seamlessly integrated with other frameworks and libraries.  
  
---
## ✨ Key Features  
- **Lightweight & Flexible**: Unlike complex frameworks, you can pick and choose only the functions you need, keeping your project light and agile.  
- **Intuitive DOM Control**: [dynamic.js](/js/2.1/dynamic.js) allows you to create and manipulate HTML elements programmatically with a clean, chainable syntax.  
- **Effortless SPAs**: Implement Single Page Applications with multiple sub-pages intuitively using `Fragment` and `FragMutation`.  
- **Reactive Data Management**: Use [livedata.js](/js/2.1/livedata.js) to create data objects that automatically trigger actions upon modification, simplifying state management.  
  
---
## 🚀 Getting Started  
You have two ways to include these libraries in your project.  
I recommend `Rose.js` for modern projects, while `jade.js` is available for legacy support.  
  
### 📦 Using `Rose.js` (v2.x, Recommended)  
For modern projects, use the Rose.js module loader to import the 2.x versions of the libraries.  
It leverages native ES Modules for asynchronous and efficient loading.
  
**1. Create a module loader file (e.g., module.js)**  
This file handles the asynchronous loading of all required libraries and exports them as a single default object.  
This makes it easy to add more libraries in the future.  
```javascript
import loadModule from "https://hynrusang.github.io/lib/js/Rose.js";

const [Dynamic, LiveData] = await Promise.all([
    loadModule("dynamic", "2.1"),
    loadModule("livedata", "2.0")
]);

// Bundle all modules into a single object for export
export default { Dynamic, LiveData };
```  
  
**2. Import and use the modules in your app**  
Now, your main application file can import the already-loaded modules from your local module.js file and begin using them immediately.  
```javascript
// your-main-script.js
import { Dynamic } from "./module.js";

const name = prompt("what's your name?"); 
document.body.appendChild(Dynamic.$("h1", { text: `hello, ${name}!` }).node);
```  
  
### 📜 Using `jade.js` (v1.x, Legacy)  
For older projects or non-module environments, you can use the `jade.js` script loader.
  
1. **Add the `jade.js` script to your HTML**:  
List the libraries and their desired versions inside the `<script>` tag.
```html
<script src="https://hynrusang.github.io/lib/js/jade.js">
    dynamic, release;
    livedata, release;
</script>
```  
  
2. **Add your custom scripts using the `<jade>` tag**:  
To ensure your scripts execute after the libraries are loaded, use the `<jade>` tag instead of `<script>`.  
These will be automatically converted and appended to the body.
```html
<jade src="https://www.gstatic.com/firebasejs/8.6.5/firebase-app.js"></jade>
<jade src="/resource/js/myApp.js"></jade>
```  

---
## 🎨 [dynamic.js](/js/2.1/dynamic.js) - The Dynamic UI Library  
`dynamic.js` provides tools to build and manage your UI programmatically.  

### ⚙️ `$` (or `new DocumentContainer`)  
The `$` function is a factory for creating `DocumentContainer` objects,  
which wrap HTML elements and provide a chainable API for modifying them.  
```javascript
// Assuming 'Dynamic' module has been loaded
const { $, scan, snipe, Fragment, FragMutation } = Dynamic;

const card = $("div", { id: "main-card", class: "card-container" }).add(
    $("h1", { class: "title", text: "Welcome!" }),
    $("p", { html: "This library helps you build <strong>dynamic UI</strong>." }),
    $("button", {
        text: "Click Me",
        onclick: () => alert("Button was clicked!")
    })
);

// Append the created element to the body
document.body.appendChild(card.node);
```  
  
### 🎯 `scan`  
A wrapper for `document.querySelector` and `querySelectorAll`.  
It returns native `HTMLElement`(s). Use a `!` prefix in the selector to get all matching elements.  
```javascript
// Get a single element by its ID
const titleElement = scan("#main-title");

// Get all list items as a NodeList
const allItems = scan("!ul > li");
allItems.forEach(item => console.log(item.innerText));
```  
  
### 🎯 `snipe`  
Similar to `scan`, but it returns `DocumentContainer` instance(s) instead of native elements.  
This allows you to immediately chain methods like `.set()` or `.add()`.  
```javascript
// Get a single element and chain a method
snipe("#main-title").set({ style: "color: blue;" });

// Get multiple elements and apply a class to all of them
snipe("!button").forEach(button => button.set({ class: "btn-primary" }));
```  
  
### 🧩 `new Fragment`  
A `Fragment` represents an independent, swappable section of your UI, like a page or a component.  
Each `Fragment` is rendered into a `<fragment>` tag with a matching `rid` (route ID).  
```javascript
// Create a Fragment with the rid "mainPage"
const mainPage = new Fragment("mainPage",
    $("h2", { text: "Main Page" }),
    $("p", { text: "This is the content of the main page." })
).registAnimation("fade", 500); // Apply a 0.5s fade animation on launch
```  
  
### 🚀 `FragMutation`  
`FragMutation` manages multiple `Fragment`s, enabling you to build a Single Page Application.  
It handles the lifecycle of fragments, caches their state, and renders them into a single `<fragmentbox>` element in your HTML.  
  
- Html  
```html
<body>
    <nav>
        <button id="btn-main">Main</button>
        <button id="btn-about">About</button>
    </nav>
    <fragmentbox></fragmentbox>
</body>
```  
  
- Javascript  
```javascript
const mainPage = new Fragment("main", /* ... */);
const aboutPage = new Fragment("about", /* ... */);

// Switch between fragments
document.getElementById("btn-main").onclick = () => FragMutation.mutate(mainPage);
document.getElementById("btn-about").onclick = () => FragMutation.mutate(aboutPage);

// Load the initial page
FragMutation.mutate(mainPage);
```  
`FragMutation.mutate` intelligently caches the last state of a `Fragment`.  
To force a full refresh every time, use `mutate(fragment, null, true)`.  
  
---
## 🔄 [livedata.js](/js/2.1/livedata.js) - The Reactive Data Library  
`livedata.js` provides reactive data objects that allow you to observe changes and automatically trigger side effects.  
  
### ⚙️ `$` (or `new LiveData`)  
The core of the library. It creates a `LiveData` object that holds a value.  
By providing an `observer` callback, you can execute code whenever the value changes.  
```javascript
// Assuming 'LiveData' module is loaded and its '$' is aliased to '$L'
const { $L } = { $L: LiveData.$ };

const countDisplay = document.getElementById("count");

// Create a LiveData object. The observer updates the UI on change.
const count = $L(0, {
    type: Number, // Optional: enforce a type
    observer: () => {
        countDisplay.innerText = `Current count: ${count.value}`;
    }
});

// Changing the value automatically triggers the observer
count.value = 5;  // UI updates to "Current count: 5"
count.value += 10; // UI updates to "Current count: 15"
```  
  
### 📦 `LiveManager`  
A `LiveManager` provides a safe and organized way to manage a group of related `LiveData` objects.  
```javascript
const userProfileManager = new LiveManager({
    username: $L("Guest", { type: String }),
    isLoggedIn: $L(false, {
      type: Boolean,
      observer: () => {
        if (this.value) console.log("Logined!");
      }
  })
});

// Read a value
console.log(userProfileManager.value("username")); // "Guest"

// Update a value (this will trigger the observer of the corresponding LiveData object)
userProfileManager.value("username", "Hwanryusang");
userProfileManager.value("isLoggedIn", true); // Outputs: Logined!

// Get all current values as a plain object
console.log(userProfileManager.toObject());
// Outputs: { username: "Hwanryusang", isLoggedIn: true }
```  
  
#### 💡 Helper Functions  
Each library includes a `help()` function that logs a summary of its features and API to the console.  
```javascript
Dynamic.help();
LiveData.help();
```  
  
---