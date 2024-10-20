# lib
Integrated custom libraries by programming language
## js
### Management CDN
#### [jade.js](/js/jade.js)
As an external CDN, you may need this CDN to automatically manage the 1.x version library below.  
If you want to use it, you will need to add your html tag as in the following example  
(or statically add the 1.x version of script directly below)
```html
<script src="https://hynrusang.github.io/lib/js/jade.js">
    dynamic, release;
    livedata, release;
</script>
```
notice:
Unlike other CDNs, jade.js has some framework properties.  
Therefore, if you want to create another script that interacts with the script that is automatically imported into this CDN,  
you should use not script tag but jade tag as an external script method.  
(internal scripting is not allowed)
```html
<jade src="https://www.gstatic.com/firebasejs/8.6.5/firebase-app.js"></jade>
<jade src="/resource/js/pageImport.js"></jade>
...
```
#### [Rose.js](/js/Rose.js)
As an external Module, you may need this CDN to automatically manage the 2.x version library below.  
If you want to use it, you will need insert the following script into the external module script.
```js
import loadModule from "https://hynrusang.github.io/lib/js/Rose.js";
const [Dynamic, LiveData] = await Promise.all([
    loadModule("dynamic", "release"),
    loadModule("livedata", "release")
]);
```
### Library
#### [dynamic.js](/js/2.1/dynamic.js)
1. Provides useful functions and classes for easy addition, change, and deletion of various dynamic elements.
2. Instead of frameworks like react or vue, it's a library that works directly on the web, so you can use other frameworks and libraries together.
3. It is easy to implement scenarios where there are multiple sub-pages on one page.
4. Beyond single-page applications, you can easily move tabs, switch pages, and more with just one url, FragMutation.mutate and Fragment.launch.
#### [livedata.js](/js/2.0/livedata.js)
1. It provides a class that detects changes in data and a class that safely manages multiple liveable objects.
2. If desired, you can also proceed with an additional type check to prevent value conversion to mismatched types.
