const versionInfo = {};
const versionParser = version => {
}
const get = async (name, version) => {
  const roseModule = await import(`./${versionParser(version)/${name}.js`);
  return roseModule
}
