import loadModule from "../../Rose.js";

const [Dynamic, LiveData] = await Promise.all([
    loadModule("dynamic", "developer"),
    loadModule("livedata", "developer")
])

export { Dynamic, LiveData }