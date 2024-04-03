/*
rose library
info:
This library can download other JavaScript programes by module from my website.
It was very simple and powerful to use.

How it works:
Inside the script tag to your site by type=module.
Also, write in the script similar to the following.

// ex: rose.js
import getm from "https://hynrusang.github.io/lib/js/rose.js";

const dynamic = getm("dynamic", "prerelease");
...

export { dynamic, ... }
//

Then, to access the module imported into rose, you can do the following.

// ex: other.js
import dynamic from "./rose.js";
dynamic.Fragment(...);
//

Whole picture:
*/
const versionInfo = {};
const versionParser = version => {
}
const get = async (name, version) => {
  const roseModule = await import(`./${versionParser(version)/${name}.js`);
  return roseModule
}
