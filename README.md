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
#### 사용법
1. 기본적인 dynamic.js의 사용법은 다음과 같습니다.
##### $
동적으로 다룰 수 있는 html dom을 만들어서 FragDom 객체에 담아 반환합니다.  
  
예를 들어
```js
const introduce = $("div", {id: "container", style: "width: 150px; height: 300px;"}).add(
    $("h1", {text: "hello, world!"})
)
```
는 안에 'hello, world!'라는 문자열을 가진 h1 태그를 담은 150×300 크기의 id가 container인 div 객체를 생성합니다.
##### new Fragment
개발자가 임의적으로 처리할 수 있는 하나의 동적 페이지 요소를 정의합니다.  
해당 요소는 내부 html에 별도의 rid를 가진 &lt;fragment&gt; 태그를 필요로 합니다.  
(또는 이후 후술할 FragMutation에 위임하십시오.)  
  
예를 들어
```js
const Sec1 = new Fragment("mainSection",
    introduce
).registAnimation("swip", 1000)
Sec1.launch()
```
는 launch될 때 swip 애니메이션을 1s(1000ms)동안 실행하면서 mainSection이라는 rid를 가진 &lt;fragment&gt; 내부를 동적으로 **변경**합니다.
##### FragMutation
앞서 사용했던 Fragment는 동적으로 요소를 변경하기 때문에 도중에 다른 Fragment로 전환할 경우, 이전에 했던 행동이 기억되지 않을 수 있습니다. 그리고 이는 스마트폰의 다중 애플리케이션같이, 동일 사이트에서 브라우저의 탭과 비슷한 기능 구현에 에로가 발생할 수 있습니다.  
서로 다른 rid를 가진 &lt;fragment&gt; 태그를 두개 이상 두고 Fragment도 rid 값을 다르게 준 뒤, 특정 event listener에 적절한 기능을 직접 구현하는 식으로 구현할 수 있지만, FragMutation은 보다 더 쉬운 방식과 효율적인 방식으로 이를 지원합니다.  
해당 요소는 내부 html에 별도의 rid를 가진 &lt;fragmentbox&gt; 태그를 필요로 합니다.  
Fragmentbox의 동작에는 내부 &lt;fragment&gt;들을 동적으로 관리하는 기능도 있으므로 &lt;fragment&gt; 요소는 가급적 사용하지 마십시오.  
  
예를 들어
```js
FragMutation.mutate(Sec1)
```
은 현재 실행중인 rid와 전환하려는 Fragment의 rid를 비교해서 fragment.launch를 단순 실행시키거나 대신 전환하려는 rid에 캐시해둔, 이전에 실행했던 Fragment를 복구하는 작업을 수행합니다.  
캐시되어있는 Fragment가 없을 경우, 내부적으로 &lt;fragment&gt; 요소를 자동으로 할당하고 캐시해둡니다.  
FragMutation이 할 수 있는 일은 이보다 더 다양하고 다소 복잡하므로, 추가적인 작업은 내부 문서를 참조해 주십시오.
#### [livedata.js](/js/2.0/livedata.js)
1. It provides a class that detects changes in data and a class that safely manages multiple liveable objects.
2. If desired, you can also proceed with an additional type check to prevent value conversion to mismatched types.
