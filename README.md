# lib  
Integrated custom libraries by programming language  

## js  
A collection of lightweight JavaScript libraries designed to create dynamic web pages and manage data with ease.  
This library is dependency-free, works without any build steps, and can be seamlessly integrated with other frameworks and libraries.  
  
---
### ✨ Key Features  
- **Lightweight & Flexible**: Unlike complex frameworks, you can pick and choose only the functions you need, keeping your project light and agile.  
- **Intuitive DOM Control**: [dynamic.js](/js/2.1/dynamic.js) allows you to create and manipulate HTML elements programmatically with a clean, chainable syntax.  
- **Effortless SPAs**: Implement Single Page Applications with multiple sub-pages intuitively using `Fragment` and `FragMutation`.  
- **Reactive Data Management**: Use [livedata.js](/js/2.1/livedata.js) to create data objects that automatically trigger actions upon modification, simplifying state management.  
  
---
### 🚀 Getting Started  
You have two ways to include these libraries in your project.  
I recommend `Rose.js` for modern projects, while `jade.js` is available for legacy support.  
  
#### 📦 Using `Rose.js` (v2.x, Recommended)  
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
  
#### 📜 Using `jade.js` (v1.x, Legacy)  
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
### 🎨 [dynamic.js](/js/2.1/dynamic.js) - The Dynamic UI Library
