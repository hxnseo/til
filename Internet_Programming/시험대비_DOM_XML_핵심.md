# 시험 직전 핵심 정리 (HTML DOM + XML)

> ⭐ = 시험 단골 / 🖊️ = 손코딩 출제확률 높음

---

# PART 1. HTML DOM

## 1. DOM이란
- HTML을 **객체(object) 트리**로 바꿔 JS가 조작할 수 있게 해주는 **객체 모델 + API**
- 두 정체성: ① 객체 모델 ② JS용 API
- 노드 4종: **Document / Element / Attribute / Text**
- 웹페이지 로드 → 브라우저가 트리 형태 객체로 구성, **모든 element마다 DOM 노드 1개**

## 2. 요소 찾기 메서드 ⭐⭐⭐ 🖊️
| 메서드 | 찾는 기준 | 반환값 |
|---|---|---|
| `getElementById(id)` | id | **요소 하나** |
| `getElementsByTagName(name)` | 태그 이름 | HTMLCollection (여러 개) |
| `getElementsByClassName(name)` | class 이름 | HTMLCollection (여러 개) |
| `querySelectorAll(CSS선택자)` | CSS 선택자 | NodeList (매칭 전부) |
| `querySelector(CSS선택자)` | CSS 선택자 | **첫 번째 하나** |

**암기 포인트**
- `getElements...` (복수형 s) → 여러 개 반환 → **`[0]`처럼 인덱스로 꺼내야 함** 🖊️
- `getElementsByTagName`은 document뿐 아니라 **특정 요소(x.getElementsByTagName)** 로도 호출 가능 → 검색 범위 좁히기 가능
- `getElementsByClassName("intro")` → class가 intro면 **태그 상관없이 전부**
- `querySelectorAll("p.intro")` → **p이면서 동시에 class=intro** (더 정밀)

```js
let x = document.getElementById("main");
let y = x.getElementsByTagName("p");
document.getElementById("demo").innerHTML = y[0].innerHTML; // main 안 첫 p
```

## 3. innerText vs innerHTML vs textContent ⭐시험 단골
대상: `<p id="demo"> has extra spacing and contains <span>a span element</span>.</p>`

| 속성 | 공백(spacing) | 안쪽 태그(span) |
|---|---|---|
| `innerText` | ❌ 정리됨 | ❌ 제거 |
| `innerHTML` | ✅ 유지 | ✅ 태그째 유지 |
| `textContent` | ✅ 유지 | ❌ 제거(텍스트만) |

- **innerText** = 사람 눈에 보이는 렌더링된 텍스트 (공백 정리됨)
- **innerHTML** = HTML 그대로 (태그 포함) → 태그 넣을 거면 이거
- **textContent** = 소스 상의 날것 텍스트 (태그만 빼고 공백 원본 유지)

## 4. 속성·스타일 변경 🖊️
| 방법 | 설명 |
|---|---|
| `element.속성 = 새값` | 속성값 직접 변경 |
| `element.setAttribute(속성, 값)` | 속성 추가/변경 (이미 있으면 값만 바뀜) |
| `element.style.속성 = 새스타일` | CSS 스타일 변경 |

```js
document.getElementById("image").src = "landscape.jpg";
// 또는
el.setAttribute("src", "landscape.jpg"); // 결과 동일
```

## 5. classList ⭐ (setAttribute보다 안전)
- `setAttribute("class", ...)` → class **전체를 통째로 덮어씀** (기존 클래스 날아감!)
- `classList`는 기존 거 유지하고 하나씩 추가/제거 → **더 안전**

| 메서드 | 동작 |
|---|---|
| `classList.add(c1, c2...)` | 클래스 추가 |
| `classList.remove(c1...)` | 클래스 제거 |
| `classList.contains(c)` | 있는지 검사 → true/false |

```js
document.getElementById("myDIV").classList.add("mystyle"); // 기존 유지 + 추가
el.setAttribute("class", "mystyle");                       // 기존 다 지움
```

## 6. JS로 스타일 직접 변경 → CSS 속성명 camelCase! ⭐ 🖊️
- CSS는 하이픈(`font-family`), **JS에서는 하이픈이 뺄셈으로 오해 → camelCase**

| CSS 원래 이름 | JS style |
|---|---|
| `font-family` | `style.fontFamily` |
| `font-size` | `style.fontSize` |
| `background-color` | `style.backgroundColor` |
| `color` (한 단어) | `style.color` |

```js
document.getElementById("p2").style.color = "blue";
document.getElementById("p2").style.fontFamily = "Arial";
document.getElementById("p2").style.fontSize = "larger";
```

## 7. 요소 추가/삭제 🖊️
| 메서드 | 동작 |
|---|---|
| `document.createElement("tag")` | 새 요소 생성 (아직 화면엔 없음) |
| `parent.appendChild(node)` | 자식 맨 끝에 추가 |
| `parent.replaceChild(new, old)` | old를 new로 교체 |
| `parent.insertBefore(new, old)` | old 바로 앞에 new 삽입 |
| `parent.removeChild(node)` | 자식 목록에서 제거 |
| `node.remove()` | 자기 자신 제거 |

**헷갈리는 포인트**
1. `createElement`만으로는 **화면에 안 보임** → 반드시 `appendChild`/`insertBefore`로 트리에 붙여야 보임
2. 인자 순서 `(new, old)` — replaceChild, insertBefore 둘 다 **새 노드 먼저, 기존 노드 뒤**
3. `removeChild` = 부모가 자식 떼기 / `remove()` = 자기 자신 제거

---

# PART 2. XML

## 1. Markup 개념
- Markup = 텍스트를 유용하게 쓰려고 추가하는 정보
- 두 목적: ① 처리 기능 명시 ② 논리적 요소 분리
- 두 종류:
  - **Procedural**(절차적) = 외형/표시 방법 (단점: time-consuming, re-formatting, error-prone)
  - **Descriptive/Declarative/Generalized**(기술적) = 논리적 구조 → **이 수업에서 다루는 것**
- 핵심 사상: **표현(presentation)과 구조(structure) 분리**
  - `CONTENT + STRUCTURE → FORMATTER → PRESENTATION`

## 2. XML 기본
- **Meta Language** = HTML 같은 markup language를 **정의할 수 있는 언어**
- HTML은 태그가 정해짐, XML은 **의미에 맞게 직접 태그를 만들어 씀**
- XML parser(= XML Processor): .xml 구성요소 식별 → 데이터 구조에 저장
- 설계원칙 중 시험 포인트: **Terseness is of minimal importance** (간결함보다 명확성)

## 3. XML 문법 규칙 ⭐⭐⭐ (자주 틀리는 것)
- 주석: `<!-- ... -->`
- **root element는 반드시 하나** (single outermost element)
- element 이름: 글자/숫자/언더스코어(_)/하이픈(-)/마침표(.) 가능
  - **첫 글자는 글자 또는 _ 로 시작** (숫자로 시작 ❌)
  - **`xml`로 시작 ❌** (예약어)
- **XML 선언부** `<?xml version="1.0"?>`
  - 올 거면 **맨 앞에 공백조차 오면 안 됨** → 항상 코드 첫 줄
  - `<? ... ?>` = 처리 명령(processing instruction), 닫는 태그 쌍 없음
- **case sensitive** → 시작/종료 태그 대소문자 다르면 오류
- **모든 시작 태그는 종료 태그 필수** (HTML과 달리 엄격)
- **중첩(nesting) 정확히** → `<x><y>..</x></y>` ❌ / `<x><y>..</y></x>` ✅
- **속성값 반드시 따옴표(" " 또는 ' ')로 감싸기**
- 마크업 문자(`<`, `>`, `&`)는 **character entity reference**로: `&lt;` `&gt;` `&amp;`

## 4. well-formed vs valid ⭐⭐⭐
- **well-formed**: XML 기본 문법만 지킴 (태그 닫기, 올바른 중첩, 속성값 따옴표, single root). **DTD 불필요**
- **valid**: well-formed + **DTD/스키마가 정한 규칙까지 다 지킴**
- 관계: **Valid ⊃ Well-formed** (valid면 무조건 well-formed, 역은 성립 안 함)
- DTD는 **의무 아님** (overhead 발생 → 파서가 검증 추가작업 + 외부 DTD면 네트워크 요청)
- **DTD 한계: element/attribute의 데이터 타입을 기술 못 함** (예: '정수만 허용' 불가)

```xml
<!-- 잘못된 예: root 2개라 well-formed조차 아님 -->
<greeting>Hello</greeting>
<response>Hi</response>

<!-- 올바른 예: 하나로 감쌈 -->
<conversation>
  <greeting>Hello</greeting>
  <response>Hi</response>
</conversation>
```

## 5. DOCTYPE 선언
```xml
<!DOCTYPE letter SYSTEM "letter.dtd">
```
- `letter` = **root 요소 이름** (실제 root와 일치해야 함)
- `SYSTEM` = DTD가 외부 파일/URL에 있음 (개인·로컬용)
- `PUBLIC` = 널리 공개된 표준 DTD (공식 식별자 + URL)

## 6. DTD 문법 — Markup Declarations 5종 ⭐
1. **Document type**: `<!DOCTYPE book [ ... ]>`
2. **Element**: `<!ELEMENT chapter (title?, section+)>`, `<!ELEMENT br EMPTY>`
3. **Attribute**: `<!ATTLIST section name ID #REQUIRED>`
4. **Entity**: `<!ENTITY XML "eXtensible Markup Language">`
5. **Notation**: `<!NOTATION GIF87A SYSTEM "GIF">`

### Element 선언 + content model 🖊️⭐⭐⭐
```
<!ELEMENT anthology (poem+)>
<!ELEMENT poem (title?, stanza+)>
<!ELEMENT title (#PCDATA)>
<!ELEMENT stanza (line+)>
<!ELEMENT line (#PCDATA)>
```
**수량 기호 (반드시 암기!) 🖊️**
| 기호 | 의미 |
|---|---|
| `+` | 1개 이상 |
| `*` | 0개 이상 |
| `?` | 0개 또는 1개 (optional) |
| `,` | 순서대로 (sequence) |
| `\|` | 둘 중 하나 (선택) |

- `(#PCDATA)` = Parsed Character Data = **단말(terminal) element**, 텍스트만 담음
- `EMPTY` = 빈 요소 → 실제 문서에선 `<br/>`

### Attribute 선언 🖊️
```
<!ATTLIST oldjoke name   ID                  #REQUIRED
                  label  CDATA               #IMPLIED
                  status (funny|notfunny)    'funny'>
```
구조: `<!ATTLIST 요소이름 속성이름 속성타입 기본값설정>`
- 타입: `CDATA`(문자열), `ID`, 열거형 `(M | F)`
- 기본값: `#REQUIRED`(필수), `#IMPLIED`(선택), `"M"`(기본값 지정)

```
<!ATTLIST contact type CDATA #IMPLIED>      <!-- type: 문자열, 선택 -->
<!ATTLIST flag gender (M | F) "M">          <!-- M/F만, 기본 M -->
```

## 7. FAQ 예제 (DTD 손코딩 대표문제) 🖊️⭐⭐⭐
구조 분석:
- FAQ = Metadata + (Q&A part 1개 이상)
- Metadata = Subject, Author, Email, Version, Date
- Q&A = Question + Answer

```
<!ELEMENT FAQ      (METADATA, PART+)>
<!ELEMENT METADATA (SUBJECT, AUTHOR, EMAIL?, VERSION?, DATE?)>
<!ELEMENT SUBJECT  (#PCDATA)>
<!ELEMENT AUTHOR   (#PCDATA)>
<!ELEMENT EMAIL    (#PCDATA)>
<!ELEMENT VERSION  (#PCDATA)>
<!ELEMENT DATE     (#PCDATA)>
<!ELEMENT PART     (QA+)>
<!ELEMENT QA       (QUESTION, ANSWER)>
<!ELEMENT QUESTION (#PCDATA)>
<!ELEMENT ANSWER   (#PCDATA)>
<!ATTLIST PART NO    CDATA #IMPLIED
               TITLE CDATA #IMPLIED>
<!ATTLIST QA   NO    CDATA #IMPLIED>
```

## 8. Namespaces ⭐⭐ 🖊️
- 목적: **이름 충돌(naming collision) 방지**
- 각 prefix는 **URI**에 묶임 (URI는 고유 식별자일 뿐, 실제 접속 X)
- prefix 아무거나 가능, **`xml`만 예약어**

**prefix 방식**
```xml
<text:directory xmlns:text="http://www.example.org/text/"
                xmlns:image="http://www.example.org/image/">
  <text:file filename="book.xml">
    <text:description>A book list</text:description>
  </text:file>
  <image:file filename="funny.jpg">
    <image:description>A funny picture</image:description>
  </image:file>
</text:directory>
```
- `<text:file>` ≠ `<image:file>` → 이름 같아도 다른 요소로 구분
- **선언 위치 ≠ 어느 namespace에 속하는가** ⭐
  - 선언은 보통 **가장 바깥 요소**에 몰아서 함 (자식들이 다 쓸 수 있게)
  - 요소가 어느 namespace인지는 **붙은 prefix가 결정**

**default namespace 방식**
```xml
<directory xmlns="http://www.example.org/text/"
           xmlns:image="http://www.example.org/image/">
  <file ...>            <!-- prefix 없어도 default에 속함 -->
  <image:file ...>      <!-- image는 여전히 prefix 명시 -->
</directory>
```
- `xmlns="..."` (콜론 없음) = **default namespace** → prefix 안 붙은 요소들이 자동 소속
- 자주 쓰는 쪽을 default로 빼면 문서 깔끔

## 9. Entity (물리적 구조) ⭐⭐
> ELEMENT=논리적 구조 / ENTITY=물리적 구조 (데이터 저장 위치·재사용)

### 분류① Parsed vs Unparsed
- **Parsed**: 파서가 해석, 참조하면 대체텍스트로 치환. `&이름;`로 호출
  - `<!ENTITY XML "eXtensible Markup Language">`
- **Unparsed**: 파서가 내용 안 읽음(이미지 등 바이너리), notation 가짐. **NDATA 붙으면 unparsed!**
  - `<!ENTITY hatch-pic SYSTEM "../grafix/OpenHatch.gif" NDATA gif>`

### 분류② General vs Parameter ⭐ 🖊️
- **General**: 문서 **본문**용, `&이름;`로 시작/참조
- **Parameter**: **DTD 내부**용 parsed entity, 선언·참조 모두 `%` 사용
  - `<!ENTITY % ISOLat2 SYSTEM "...">` → 참조 `%ISOLat2;`
- **구분: `&`=general(본문), `%`=parameter(DTD 내부)**

### 분류③ Internal vs External
- **Internal**: 내용이 선언 안에 직접
  - `<!ENTITY Pub-Status "This is a pre-release.">`
- **External**: 외부 파일/리소스 → `SYSTEM`(경로/URL) 또는 `PUBLIC`(공개식별자+URL)
  - `<!ENTITY chapter1 SYSTEM "Chapter1.xml">`

## 10. Entity Reference vs Character Reference ⭐
- **Entity reference**: `&이름;` (이름으로) — 예약문자 `&` → `&amp;`
- **Character reference**: `&#숫자;` (유니코드 코드값으로 직접)
  - Decimal: `&#8478;` / Hexadecimal: `&#x211E;` (x 붙으면 16진수)
- **참조는 한 번에 다 안 풀리고 단계별로 해석됨** ⭐⭐⭐
  - `&#38;` = `&` (38이 &의 코드값)
  - 코드값: `#37`=`%`, `#60`=`<`, `#38`=`&`

## 11. CDATA Section ⭐ 🖊️
- 파서에게 **마크업 문자 무시**하라고 지시 → 안의 내용은 문법 해석 X, **문자 그대로**
- `<`, `&` 같은 예약문자를 `&lt;`, `&amp;`로 안 바꿔도 됨 → **소스 코드 넣을 때 유용**
- ⚠️ CDATA **섹션** ≠ CDATA **속성 타입** (다른 개념!)
- ⚠️ 종료표시 `]]>` 자체는 내용에 못 넣음

```xml
<![CDATA[
  *p = &q;
  b = (i <= 3);
]]>
```

## 12. XML Vocabularies (XML로 만든 표준 언어)
- **MathML**: 수식 (`<math>`, `<mrow>`행, `<mi>`변수, `<mo>`연산자, `<mn>`숫자, `<mfrac>`분수, `<msqrt>`제곱근, `<msup>`위첨자)
- **SVG**: 벡터 그래픽 (XML 기반, 태그로 그래픽 기술)
- SMIL(멀티미디어), MusicXML(악보), CML(화학), XBRL(비즈니스)

---

## 🎯 시험 직전 최종 체크
- [ ] 요소 찾기 5개 메서드 + 복수형은 `[0]`
- [ ] innerText/innerHTML/textContent 차이 (공백·태그)
- [ ] style은 camelCase (fontFamily, fontSize, backgroundColor)
- [ ] createElement → appendChild 안 하면 안 보임 / insertBefore(new, old)
- [ ] well-formed vs valid (Valid ⊃ Well-formed)
- [ ] DTD 수량기호: `+ * ? , |`
- [ ] FAQ DTD 손코딩
- [ ] namespace: 선언위치 ≠ 소속, xmlns(default) vs xmlns:prefix
- [ ] entity 3분류: Parsed/Unparsed(NDATA), General(&)/Parameter(%), Internal/External
- [ ] CDATA 섹션 vs CDATA 속성 / `]]>` 못 넣음
- [ ] 참조는 단계별로 해석됨
