package home.books;

import static java.nio.charset.StandardCharsets.UTF_8;

import home.Tuple2;
import home.Utils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

class LearningRustInTest {
  private static final String FILENAME =
      Utils.decodeThenDecryptThenDecode(
          "iVAjZOE2mLRT5AxOqLzbKl4tbMH0PYEIXapClpWiPMLCCMvfdC33DeN68tloPe0G4_Iep4wyigwqc5MKFeFMNA");
  private static final Path PATH_TXT = Path.of("src/test/resources", FILENAME);
  private static final Path PATH_HTML =
      Path.of("src/test/resources", FILENAME.replace(".txt", ".html"));
  private static final String CSS =
      """
      :root {
        --pre-background: #f1f6fa;
      }
      a { text-decoration: none }
      pre { background: linear-gradient(90deg, var(--pre-background), #f9fdff, var(--pre-background));
        padding: .7rem 1rem; margin-left: 1rem
      }
      code.fm-code-in-text { background: var(--pre-background); padding: .1rem .2rem }
      pre span.fm-combinumeral { font-size: 1.4rem; line-height: 1 }
      p.fm-code-annotation { margin-top: .2rem; margin-bottom: .2rem }
      li > p { margin-top: .2rem; margin-bottom: .2rem }
      div.orm-ChapterReader-codeSnippetContainer { display: flex }
      div.orm-ChapterReader-snippetButtonContainer { margin-top: 1rem; margin-left: 1rem }
      p + div.orm-ChapterReader-codeSnippetContainer > pre { margin-top: 0 }
      .float-left { float: left }
      .clear-both { clear: both }
      h1, h2, h3 { font-family: 'Noto Sans' }
      h1 { font-size: 1.67rem; font-weight: 300; color: #666 }
      h2 { font-size: 1.32rem; font-weight: 400; color: #26f }
      h3 { font-size: 1.15rem; font-weight: 400; color: #f26 }
      h1.tochead { margin-top: 4em } h1.tochead:first-child { margin-top: 0 }
      h2.fm-head + div.flex > * { margin-top: 0 }
      div.flex { display: flex; position: relative }
      div > div.temp { position: absolute; top: 0; left: 0; padding: .5rem; background-color: rgba(255, 255, 255, .8); display: none }
      div.flex > pre.programlisting + div.temp + p, div.flex > pre.programlisting + * + div.temp + p { margin-left: 1rem }
      p + div.flex > * { margin-top: 0 }
      p.fm-callout { margin-left: 3em; margin-right: 2em }
      p.fm-callout > span.fm-callout-head {
        display: inline-block; margin-right: 1em; font-family: 'Noto Sans', sans-serif;
        font-weight: 700
      }
      div#toc { position: fixed; top: 0; right: 3rem; background-color: rgba(255, 255, 255, .9);
        max-height: 82vh; overflow: auto; z-index: 9; padding: 1rem; padding-top: .1rem;
        padding-bottom: .5rem; border: 1px solid #ccc; border-top: 0
      }
      #toc-chkbox + div { display: none } #toc-chkbox:checked + div { display: unset }
      div#toc a { display: block; line-height: 1.45; color: #333 }
      div#toc a:hover { color: #26f }
      div#toc a.toc-1 { padding-top: 1rem }
      div#toc a.toc-2 { padding-left: 1rem }
      div#toc a.toc-3 { padding-left: 2rem }
      div#toc a.toc-4 { padding-left: 3rem }
      span.fm-combinumeral { font-size: 120% }
      p.fm-head2 { font-weight: 500; color: #5d11d6}""";

  @Test
  void test() throws Throwable {
    Tuple2<String, Map<Integer, String>> tuple =
        Utils.extractPres(Files.readString(PATH_TXT, UTF_8));
    var document = Jsoup.parse(tuple._1());

    Utils.addStuff(
        document,
        Utils.decodeThenDecryptThenDecode(
            "I-UppN2YhY3PJna2uTeDPKnBqOpm4Fe7QbH7dtQq25u4V9jRMRRbG03oA-OmlHXqHEvZb5E5cIsdTeP1lZpqJQ"),
        CSS);

    // specific for each book
    document
        .body()
        .append(
            """
            <script>
              (function() { // this creates a function names sha1, call it like this: sha1('...')
                'use strict';

                var INPUT_ERROR = 'input is invalid type';
                var FINALIZE_ERROR = 'finalize already called';
                var WINDOW = true;
                var root = window;
                var WEB_WORKER = false;
                var NODE_JS = false;
                var COMMON_JS = false;
                var AMD = false;
                var ARRAY_BUFFER = true;
                var HEX_CHARS = '0123456789abcdef'.split('');
                var EXTRA = [-2147483648, 8388608, 32768, 128];
                var SHIFT = [24, 16, 8, 0];
                var OUTPUT_TYPES = ['hex', 'array', 'digest', 'arrayBuffer'];

                var blocks = [];

                var isArray = Array.isArray;
                var isView = ArrayBuffer.isView;

                var formatMessage = function (message) { // [message: string, isString: bool]
                  var type = typeof message;
                  if (type === 'string') {
                    return [message, true];
                  }
                  if (type !== 'object' || message === null) {
                    throw new Error(INPUT_ERROR);
                  }
                  if (ARRAY_BUFFER && message.constructor === ArrayBuffer) {
                    return [new Uint8Array(message), false];
                  }
                  if (!isArray(message) && !isView(message)) {
                    throw new Error(INPUT_ERROR);
                  }
                  return [message, false];
                }

                var createOutputMethod = function (outputType) {
                  return function (message) {
                    return new Sha1(true).update(message)[outputType]();
                  };
                };

                var createMethod = function () {
                  var method = createOutputMethod('hex');
                  method.create = function () {
                    return new Sha1();
                  };
                  method.update = function (message) {
                    return method.create().update(message);
                  };
                  for (var i = 0; i < OUTPUT_TYPES.length; ++i) {
                    var type = OUTPUT_TYPES[i];
                    method[type] = createOutputMethod(type);
                  }
                  return method;
                };

                function Sha1(sharedMemory) {
                  if (sharedMemory) {
                    blocks[0] = blocks[16] = blocks[1] = blocks[2] = blocks[3] =
                    blocks[4] = blocks[5] = blocks[6] = blocks[7] =
                    blocks[8] = blocks[9] = blocks[10] = blocks[11] =
                    blocks[12] = blocks[13] = blocks[14] = blocks[15] = 0;
                    this.blocks = blocks;
                  } else {
                    this.blocks = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
                  }

                  this.h0 = 0x67452301;
                  this.h1 = 0xEFCDAB89;
                  this.h2 = 0x98BADCFE;
                  this.h3 = 0x10325476;
                  this.h4 = 0xC3D2E1F0;

                  this.block = this.start = this.bytes = this.hBytes = 0;
                  this.finalized = this.hashed = false;
                  this.first = true;
                }

                Sha1.prototype.update = function (message) {
                  if (this.finalized) {
                    throw new Error(FINALIZE_ERROR);
                  }

                  var result = formatMessage(message);
                  message = result[0];
                  var isString = result[1];
                  var code, index = 0, i, length = message.length || 0, blocks = this.blocks;

                  while (index < length) {
                    if (this.hashed) {
                      this.hashed = false;
                      blocks[0] = this.block;
                      this.block = blocks[16] = blocks[1] = blocks[2] = blocks[3] =
                      blocks[4] = blocks[5] = blocks[6] = blocks[7] =
                      blocks[8] = blocks[9] = blocks[10] = blocks[11] =
                      blocks[12] = blocks[13] = blocks[14] = blocks[15] = 0;
                    }

                    if (isString) {
                      for (i = this.start; index < length && i < 64; ++index) {
                        code = message.charCodeAt(index);
                        if (code < 0x80) {
                          blocks[i >>> 2] |= code << SHIFT[i++ & 3];
                        } else if (code < 0x800) {
                          blocks[i >>> 2] |= (0xc0 | (code >>> 6)) << SHIFT[i++ & 3];
                          blocks[i >>> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
                        } else if (code < 0xd800 || code >= 0xe000) {
                          blocks[i >>> 2] |= (0xe0 | (code >>> 12)) << SHIFT[i++ & 3];
                          blocks[i >>> 2] |= (0x80 | ((code >>> 6) & 0x3f)) << SHIFT[i++ & 3];
                          blocks[i >>> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
                        } else {
                          code = 0x10000 + (((code & 0x3ff) << 10) | (message.charCodeAt(++index) & 0x3ff));
                          blocks[i >>> 2] |= (0xf0 | (code >>> 18)) << SHIFT[i++ & 3];
                          blocks[i >>> 2] |= (0x80 | ((code >>> 12) & 0x3f)) << SHIFT[i++ & 3];
                          blocks[i >>> 2] |= (0x80 | ((code >>> 6) & 0x3f)) << SHIFT[i++ & 3];
                          blocks[i >>> 2] |= (0x80 | (code & 0x3f)) << SHIFT[i++ & 3];
                        }
                      }
                    } else {
                      for (i = this.start; index < length && i < 64; ++index) {
                        blocks[i >>> 2] |= message[index] << SHIFT[i++ & 3];
                      }
                    }

                    this.lastByteIndex = i;
                    this.bytes += i - this.start;
                    if (i >= 64) {
                      this.block = blocks[16];
                      this.start = i - 64;
                      this.hash();
                      this.hashed = true;
                    } else {
                      this.start = i;
                    }
                  }
                  if (this.bytes > 4294967295) {
                    this.hBytes += this.bytes / 4294967296 << 0;
                    this.bytes = this.bytes % 4294967296;
                  }
                  return this;
                };

                Sha1.prototype.finalize = function () {
                  if (this.finalized) {
                    return;
                  }
                  this.finalized = true;
                  var blocks = this.blocks, i = this.lastByteIndex;
                  blocks[16] = this.block;
                  blocks[i >>> 2] |= EXTRA[i & 3];
                  this.block = blocks[16];
                  if (i >= 56) {
                    if (!this.hashed) {
                      this.hash();
                    }
                    blocks[0] = this.block;
                    blocks[16] = blocks[1] = blocks[2] = blocks[3] =
                    blocks[4] = blocks[5] = blocks[6] = blocks[7] =
                    blocks[8] = blocks[9] = blocks[10] = blocks[11] =
                    blocks[12] = blocks[13] = blocks[14] = blocks[15] = 0;
                  }
                  blocks[14] = this.hBytes << 3 | this.bytes >>> 29;
                  blocks[15] = this.bytes << 3;
                  this.hash();
                };

                Sha1.prototype.hash = function () {
                  var a = this.h0, b = this.h1, c = this.h2, d = this.h3, e = this.h4;
                  var f, j, t, blocks = this.blocks;

                  for(j = 16; j < 80; ++j) {
                    t = blocks[j - 3] ^ blocks[j - 8] ^ blocks[j - 14] ^ blocks[j - 16];
                    blocks[j] =  (t << 1) | (t >>> 31);
                  }

                  for(j = 0; j < 20; j += 5) {
                    f = (b & c) | ((~b) & d);
                    t = (a << 5) | (a >>> 27);
                    e = t + f + e + 1518500249 + blocks[j] << 0;
                    b = (b << 30) | (b >>> 2);

                    f = (a & b) | ((~a) & c);
                    t = (e << 5) | (e >>> 27);
                    d = t + f + d + 1518500249 + blocks[j + 1] << 0;
                    a = (a << 30) | (a >>> 2);

                    f = (e & a) | ((~e) & b);
                    t = (d << 5) | (d >>> 27);
                    c = t + f + c + 1518500249 + blocks[j + 2] << 0;
                    e = (e << 30) | (e >>> 2);

                    f = (d & e) | ((~d) & a);
                    t = (c << 5) | (c >>> 27);
                    b = t + f + b + 1518500249 + blocks[j + 3] << 0;
                    d = (d << 30) | (d >>> 2);

                    f = (c & d) | ((~c) & e);
                    t = (b << 5) | (b >>> 27);
                    a = t + f + a + 1518500249 + blocks[j + 4] << 0;
                    c = (c << 30) | (c >>> 2);
                  }

                  for(; j < 40; j += 5) {
                    f = b ^ c ^ d;
                    t = (a << 5) | (a >>> 27);
                    e = t + f + e + 1859775393 + blocks[j] << 0;
                    b = (b << 30) | (b >>> 2);

                    f = a ^ b ^ c;
                    t = (e << 5) | (e >>> 27);
                    d = t + f + d + 1859775393 + blocks[j + 1] << 0;
                    a = (a << 30) | (a >>> 2);

                    f = e ^ a ^ b;
                    t = (d << 5) | (d >>> 27);
                    c = t + f + c + 1859775393 + blocks[j + 2] << 0;
                    e = (e << 30) | (e >>> 2);

                    f = d ^ e ^ a;
                    t = (c << 5) | (c >>> 27);
                    b = t + f + b + 1859775393 + blocks[j + 3] << 0;
                    d = (d << 30) | (d >>> 2);

                    f = c ^ d ^ e;
                    t = (b << 5) | (b >>> 27);
                    a = t + f + a + 1859775393 + blocks[j + 4] << 0;
                    c = (c << 30) | (c >>> 2);
                  }

                  for(; j < 60; j += 5) {
                    f = (b & c) | (b & d) | (c & d);
                    t = (a << 5) | (a >>> 27);
                    e = t + f + e - 1894007588 + blocks[j] << 0;
                    b = (b << 30) | (b >>> 2);

                    f = (a & b) | (a & c) | (b & c);
                    t = (e << 5) | (e >>> 27);
                    d = t + f + d - 1894007588 + blocks[j + 1] << 0;
                    a = (a << 30) | (a >>> 2);

                    f = (e & a) | (e & b) | (a & b);
                    t = (d << 5) | (d >>> 27);
                    c = t + f + c - 1894007588 + blocks[j + 2] << 0;
                    e = (e << 30) | (e >>> 2);

                    f = (d & e) | (d & a) | (e & a);
                    t = (c << 5) | (c >>> 27);
                    b = t + f + b - 1894007588 + blocks[j + 3] << 0;
                    d = (d << 30) | (d >>> 2);

                    f = (c & d) | (c & e) | (d & e);
                    t = (b << 5) | (b >>> 27);
                    a = t + f + a - 1894007588 + blocks[j + 4] << 0;
                    c = (c << 30) | (c >>> 2);
                  }

                  for(; j < 80; j += 5) {
                    f = b ^ c ^ d;
                    t = (a << 5) | (a >>> 27);
                    e = t + f + e - 899497514 + blocks[j] << 0;
                    b = (b << 30) | (b >>> 2);

                    f = a ^ b ^ c;
                    t = (e << 5) | (e >>> 27);
                    d = t + f + d - 899497514 + blocks[j + 1] << 0;
                    a = (a << 30) | (a >>> 2);

                    f = e ^ a ^ b;
                    t = (d << 5) | (d >>> 27);
                    c = t + f + c - 899497514 + blocks[j + 2] << 0;
                    e = (e << 30) | (e >>> 2);

                    f = d ^ e ^ a;
                    t = (c << 5) | (c >>> 27);
                    b = t + f + b - 899497514 + blocks[j + 3] << 0;
                    d = (d << 30) | (d >>> 2);

                    f = c ^ d ^ e;
                    t = (b << 5) | (b >>> 27);
                    a = t + f + a - 899497514 + blocks[j + 4] << 0;
                    c = (c << 30) | (c >>> 2);
                  }

                  this.h0 = this.h0 + a << 0;
                  this.h1 = this.h1 + b << 0;
                  this.h2 = this.h2 + c << 0;
                  this.h3 = this.h3 + d << 0;
                  this.h4 = this.h4 + e << 0;
                };

                Sha1.prototype.hex = function () {
                  this.finalize();

                  var h0 = this.h0, h1 = this.h1, h2 = this.h2, h3 = this.h3, h4 = this.h4;

                  return HEX_CHARS[(h0 >>> 28) & 0x0F] + HEX_CHARS[(h0 >>> 24) & 0x0F] +
                         HEX_CHARS[(h0 >>> 20) & 0x0F] + HEX_CHARS[(h0 >>> 16) & 0x0F] +
                         HEX_CHARS[(h0 >>> 12) & 0x0F] + HEX_CHARS[(h0 >>> 8) & 0x0F] +
                         HEX_CHARS[(h0 >>> 4) & 0x0F] + HEX_CHARS[h0 & 0x0F] +
                         HEX_CHARS[(h1 >>> 28) & 0x0F] + HEX_CHARS[(h1 >>> 24) & 0x0F] +
                         HEX_CHARS[(h1 >>> 20) & 0x0F] + HEX_CHARS[(h1 >>> 16) & 0x0F] +
                         HEX_CHARS[(h1 >>> 12) & 0x0F] + HEX_CHARS[(h1 >>> 8) & 0x0F] +
                         HEX_CHARS[(h1 >>> 4) & 0x0F] + HEX_CHARS[h1 & 0x0F] +
                         HEX_CHARS[(h2 >>> 28) & 0x0F] + HEX_CHARS[(h2 >>> 24) & 0x0F] +
                         HEX_CHARS[(h2 >>> 20) & 0x0F] + HEX_CHARS[(h2 >>> 16) & 0x0F] +
                         HEX_CHARS[(h2 >>> 12) & 0x0F] + HEX_CHARS[(h2 >>> 8) & 0x0F] +
                         HEX_CHARS[(h2 >>> 4) & 0x0F] + HEX_CHARS[h2 & 0x0F] +
                         HEX_CHARS[(h3 >>> 28) & 0x0F] + HEX_CHARS[(h3 >>> 24) & 0x0F] +
                         HEX_CHARS[(h3 >>> 20) & 0x0F] + HEX_CHARS[(h3 >>> 16) & 0x0F] +
                         HEX_CHARS[(h3 >>> 12) & 0x0F] + HEX_CHARS[(h3 >>> 8) & 0x0F] +
                         HEX_CHARS[(h3 >>> 4) & 0x0F] + HEX_CHARS[h3 & 0x0F] +
                         HEX_CHARS[(h4 >>> 28) & 0x0F] + HEX_CHARS[(h4 >>> 24) & 0x0F] +
                         HEX_CHARS[(h4 >>> 20) & 0x0F] + HEX_CHARS[(h4 >>> 16) & 0x0F] +
                         HEX_CHARS[(h4 >>> 12) & 0x0F] + HEX_CHARS[(h4 >>> 8) & 0x0F] +
                         HEX_CHARS[(h4 >>> 4) & 0x0F] + HEX_CHARS[h4 & 0x0F];
                };

                Sha1.prototype.toString = Sha1.prototype.hex;

                var exports = createMethod();
                exports.sha1 = exports;
                root.sha1 = exports;
              })();
              const id2maxwidth = {
                '72a7c6d3b4a086d0651e4027f1734b41b4cdc18c': 35,
                'e12f38393044d25483bee2f11bb4c84d9c7f5863': 35,
                '92f036afe6192372f8db8921564a3e9e1562472d': 35,
                'c9e1ba645e5ed1068a9d0da62d7e67e682e4001b': 35,
                '10bfb6e5584ad8ff98854f3a6e44a0ee4ddbf556': 28,
                '8a427ff077df93ecb7b61fb79f033bcac4a38112': 50,
                'c11ac683ec35b96fcbd14931625a0c97cd4dd9a7': 38,
                '44915d7a1008253e17da6a55770b695447f68a70': 35,
                '153fb4f00a181032656bae756603b45bccd9ae2d': 35,
                '94a2a825abfc8329e767cde834a92a275c9bd0e5': 35,
                '6ac913f77e07298eeb899d79e554ee7b1754cc46': 35,
                'dee6a429c3cc451c360b9d0ed097355ba016af96': 35,
                '06470cd74433e706f0fb8b1a7a627522f3e4b077': 35,
                'cb56007f7f9244d9476750f13e0beb84cee17685': 56,
                'ebce03817aa98dcea1f8a51567d525d610830ff5': 36,
                'a59c98d0ff41ec3e62d9668cf0b8979b39f707c9': 45,
                'aff31a3d94477a7eb56b558f04a139fb9fec6e40': 28,
                '9b7dd0232d172ec0817b158ab59df1bca3083753': 17,
                'e6c0b237860ec54c201e22311e49330fb592be92': 45,
                'f6052396054299d623db3cc65ad1e3068a9a4676': 45,
                '2773b2bc7e00a599b13766cde8f405acf2fecae6': 45,
                'ad4a1e49a7005edd71376c48c2eccb492e879769': 35,
                'bf05f7e45bab48559e33c34a65896749d00c1326': 35,
                '1abbb3e9c173f1425924325c9ed5b7a037f59691': 35,
                '7dd9fed95fd7a07ab1544d2463b39520bb498f9b': 35,
                '56b1313b8763aca42e290784db3765b97d9e40b8': 35,
                '270149576de1525c3158176add2bb214933ed3f9': 35,
                'b799ff8f79556aba45355887a358935d767109e3': 35,
                '752f7883b88899e6939f29d3e1bf581a10213605': 35,
                'f6aa85805b3be611e9c092448cf0171aa844dce8': 35,
                '6c59b45b60997aa16c2db1105cc18d2472c98fbd': 56,
                'ac516e3f8c0f051efa3c0fde6e4ccb155840586e': 44,
                'd9098c6198dfc9ae8ad3df6a173abc05c90c9acd': 35,
                '8b1ba671de018ca68ed15d211bc31302ada72bf4': 35,
                '8b1ba671de018ca68ed15d211bc31302ada72bf4': 44,
                'da225d324fd69355c6d0d36f68a64500c3f2cbc2': 56,
                'caac77557532aa07367e2f89b61edf9a63f05d41': 24,
                'cfd6ea0ea780ca2258ba6fa5bf272d52b66753b1': 45,
                '2144da9331a75d0cd51d38acb2e5dd0c4c323761': 35,
                '24310611ecd68ed333fe940c241c29697fdadf8e': 35,
                '38390e28cee426d74e036524b623f780c42eff0d': 35,
                '549a5529ef7495531c43bc31060e0f398d1fff66': 35,
                '7a4ca22b1a19e74e609cd65dbd5a22a82807f8bf': 35,
                '7a4ca22b1a19e74e609cd65dbd5a22a82807f8bf': 44,
                'b7d7eb0d2ff2216f936b401d7ec475ca6feeab8e': 44,
                'ed93e72eaca7a14db28e22d338f81ba30bb5fb64': 44,
                'ea5318a99c0ba02ffcf3db80df238387a369a9a6': 31,
                '176114f862dc03816c6ae3fd313ea0ee0c0039fe': 55,
                '4d20fe2bb77c187da63fd10921b6618b71f8e353': 44,
                'c2073d2480da31a3c7b0de8a656df2376b07c161': 35,
                '7912d64e06c744dd7de7116f8b3b5f279f803449': 35,
                '676f810838f2004bb6e7f18df6ce4a28609ffb13': 35,
                '9c2083dfcd7f8ecf2d025d20096138afd711fcdd': 35,
                'd3112fd51b0e5ee1ec9cbd48ee1cb4f5cb7b3cdd': 55,
                'ed5849cdd56963b9dc965dc8da5c578d9f54c7ea': 16,
                'bb8c7e192b393ef0383c727703affa78ccfafe29': 35,
                '437a317b5b9ae761b1f00976cef202a816aabebf': 35,
                'e0cf3d022725471db33bc0409284ea8a075c1d84': 35,
                '52dddbf7d5a696ce2e1539e88c008ae244c5d3c5': 35,
                '91c4230a6faf5f06b2be1c36025204a6faa67b2b': 44,
                'fccdb0eff0357a3a43b68276a5b033ac39f1555f': 35,
                'ea910585e74655eb4334e0bbcb2fc225716e2d03': 35,
                '39181dd7abe8be265b0879a7e0968112c9bb27a3': 35,
                'e0f796b092beaebb19a063af83d805d09ea4cde5': 35,
                '370aaafeea807aa967446bc332b7e04fa6c34eaa': 44,
                '25f4c8a411435977fb749f49d21872831ca4cb51': 16,
                'ab74e349cae2bcc39598a11d1c9ae7e15e06c9f7': 35,
                '0a9d37d261feaf67116cb592571b51bfa6b5fc15': 35,
                'daa1067f7e2ca03fbb7025260d10c75b6749963c': 28,
                '9bded734d14bc1ab6a1e96e1693b6253a50bbb1f': 47,
                '7d636f38e38d3eac1b621df6aa166f691bfacab0': 55,
                'f26f0ae52d864a33f3e875e6f9ed67bcb3a6ea43': 55,
                'c36370eb227d13626ea2c68e3bdebf37ad33d230': 44,
                '3da7d303491b765441b442c5214220719c0ed1c1': 44,
                'd94534a8588f2e6af9ef4e635992a248bdc69c8f': 44,
                '89aca5cb7b44b3be6b4a613398f6a67d5f95458d': 44,
                'a0f9873b6c517a2fac89a0b8806ac919b3067b5d': 35,
                '91ffbb9c386d146a4de73dbb1f8fc9c0380fa8a0': 55,
                '58eb0a1988247440feb74958d2c76377a0a512bc': 35,
                'b08e401345915bd3f76315d62187fff5e4f65d69': 27,
                'f0f6585193dd39f8e1e66e54b5a03dbd84ad0dc4': 44,
                '9b6734c689ec729258fc6705af1fcd4bdab9b903': 44,
                'e07efe3210d3dba5013243e7aaede828af7aff77': 44,
                '077a34e7155027299b62205fc01ab051e482a9bf': 35,
                'd5f07508884e768070b781c5d7454aace2d831d6': 55,
                'c0ad0001b4963c5026ba1b00e7e3b417187915ab': 55,
                'fbc75995679ca9c1a7d8192d428a0c3045c1ff48': 67,
                'e194e2be4f1f85e9cfedce704cc69e3adbf99e05': 35,
                '72972b60c20bb8edcfaa16249fd410ecfa828ce4': 55,
                '9f5257d8d6cfa9fc5e6b744fe2742d0993f4412f': 55,
                'e9c19951f4c53ab9491f0036730f1f586ea2c68c': 55,
                'a4c8725cf13ac95083c91fac6b64cc356feec93e': 55,
                '70bb36782533ec3fe945aebe0f47cbf369c929f8': 55,
                'ecad485cc5e04e8117921129d656716e7b148747': 35,
                '847987e264499c93556b0928261181a183ddfa0c': 35,
                '83bf901ee89539e51d52be8da361959fa121796b': 44,
                '6b2d1620f2bc6aca0726f90ecdb3f82edc896670': 44,
                '33badf8b935d6a9a08ea78a66cc5d7ea3712446b': 44,
                '70a7cac13cf9d75d4ba62f26fe95b551b92c34be': 44,
                'e654982bf5d7dd2daf634087550bcb619e9b5d12': 55,
                'e36911606b4dfb539b8b376637a2e735166ab48f': 55,
                '4db5a0b0642259e272fe039a59a3d749b14509ba': 55,
                'ca27c755bb14d74dfb1da0a0f4fd9b94e3ae8419': 35,
                '05c1a94a3101c1394bfd966513d9fb21c0f8e0ec': 44,
                'dea88c924ce81dd1423aaffd4195cf4beba6d963': 44,
                'c27469ce18f83e7d64fd1627f142ba9d111b0da0': 55,
                'f9a7c080d0009729a30e3eaf3110a60f2ecc34f2': 55,
                '5c3847e568a77cdd6f4574d3968aa9fbe90a3ae7': 44,
                '807cf391487b772910b40798d74add12e3f7e29e': 44,
                '8de70e64e85e9f562ca6779aa19ce6cafdae129e': 44,
                '14ef7b7743f6edd53224bf31c176f6955726fe4a': 38,
                'bd510de78fd8e4aed0b2e56f701562ca021bedc9': 44,
                '61d666a021b666e621e1e6c0f952626267f51969': 32,
                'e24202f27c4d950c2630ecd42692240de96e0e4e': 55,
                'e0c6a3fe40cdce41d64f1537c7f0ae16b42eac16': 55,
                'e2bbf747f2793980f9cd1c1b9a45842660b9af6b': 55,
                '12b4f90eb522a3dd4f863dbba8231456dd71ace3': 66,
                'c28d02feaf422a18249f8b35ee3f619ec87ab1c9': 72,
                'a8b796134c378e8566ed44327928ac8932f4e780': 44,
                '9782247fa026cfa9955593cd9f2519c44c884b52': 48,
                '08513ff275f4c1040b8b4d9aa78ffc981bd097d9': 55,
                '2571d1fa2e09720e36be19f5879d93dcd5305e76': 35,
                '5a2de8de2150d57a75a1a778a72fe42f6bb5c52f': 55,
                '540dcb6969cc0450793234b9e1fc1ad5e9e95ca8': 55,
                '3204e343200a72e2251a3a8254a935b09db25348': 36,
                'c7fac59f4c7e0a0abbe263f5713bb24c18b4056c': 36,
                '07eebe99a3f9bdf114ec925da566fcb945470ce3': 55,
                '20a536bd67b0f25bc675c0cb05a15b85900e0e01': 44,
                '5b336d9c71a661a9d7a90589d79f352a84bab946': 34,
                'bbe96a88d1635d21a781a59d44ac637889f6fa6f': 34,
                'e1a697b4c7db3ce53afeb1f69cf549ceb1fc8613': 44,
                'd2a772a038cc8777c182b20f07f8a41f83893f22': 44,
                '2344eb569ad75a9eb98257321dc239750b8bb102': 44,
                '38b7b59e1181f7fe9f6e398a4f8d9af59b700627': 44,
                '2c10dc99abe23f97e0860883a469fbb5e82829fa': 55,
                '35eef2aa4a47e7db9117f726dc2ab0a9fd84e6b6': 44,
                '122672dbc3a20497eb8940fbb775bcbc1f7acb3f': 44,
                'ec0690e75b9cab95cae46ac2a3b626befe6933e0': 32,
                'f48e9d6192d19d4e1d8ed9b1f992e0f6b0bb872f': 32,
                '6a239fb15662e85ab054ad831a9119501ccee48a': 32,
                '4979d623085dd09fa7f36388d76f4601630b44b7': 32,
                '05313c19baa3878652c8a0fc5ffbec2ab71308a6': 55,
                '96ef607ee7dda7b859d97f4c13f704a8722893ba': 31,
                '5e41952f4faf0aad997af04fb698a21451f146e8': 33,
                '84267249ab95e80a8efbfa5fea7683676042b47b': 34,
                '9b29fb058df43be39f859b4562ecde5793bda4aa': 34,
                'aebd8c163a2994b2ade201258ad18f72c306a5c0': 34,
                '6458575e5d3c824cb2f55c1672f7574c81a3ae57': 62,
                '20362de77e74e5df1de967265ce6c1b175ab35db': 55,
                '41c1c99882177c119f50efd8124d189250d2a41a': 35,
                'e69dd6c2c4ddb4b86b477210bfe6644e65efba2c': 35,
                '4d4a00602e7c339295ebf6655b37db2613768812': 35,
                '6a1d97b4b3f619e6490b3159c01288ce057118b0': 35,
                '99356698fbf39c5a4440a54591df0772dee7fd27': 44,
                '74d811263c95d3db43480acd68354aa5ddacbf84': 50,
                'd63bd585957fc80a65fed969113d67227d06f525': 44,
                '97e6112a0744b18cb41294f7eccc34a705587caa': 55,
                '9b6ddb04cb378fc7056dfffb32b48150847d2de2': 44,
                'e3329bde6387474dd970c8aa7ae28566a74cb170': 55,
                'a6561c8031dc06d4ac9ffb6f901506d18640f77f': 19,
                'a43ff4004e2af74994da38cfd0b9b000aff01fb8': 27,
                '7fbca3da624bccea55b08b4ce739ed080547d1a9': 35,
                '545c244795f2ed0e5918f86e5357824bc20a0376': 44,
                '3c8b35cbeeed3876743f648defc577c91173c78f': 44,
                '6ab861eafa25103f39fdcf714693b9613485fc8a': 44,
                '1644198a493e6a3afeee6b2555847562318fa13c': 44,
                '5456e250a592f2c43cefaa28bf0f062ce94b64ae': 44,
                'c7b2f1122a20141498be41b4d20bc46c7994f509': 44,
                '70147c8335bcfbfd1d28942a27c4200180db3157': 55,
                '527433c9c3edfd063e9e1ec037af6de05e98b1dc': 44,
                '85b3663c34a44b11a63a10777deabfe3efe6d12b': 44,
                '3d64b7ea2c62ac2a58057a28b658f98acc48f7ea': 44,
                'a8886d7ab385232bacc0af5c836e35e82bf3f9f8': 55,
                'fc40c7a9f5adb2063bd20b897caef2f0c422254c': 33,
                '7e6103f517d67facf35fdd5fdb0af87c7b4fa2ba': 25,
                'ddf85f4d79edc4d0caa0176c085c7779611686ed': 55,
                '0ad5730822846104e712d2fa6089374ed66f4edf': 55,
                '53ef1b17f809bb07948907498fb62a33edca3ace': 44,
                '37ff8e6356c6010eceb7b1058dc9e1a68912a156': 44,
                'c272dd3b9bed006d949311882583d1e47f629f99': 33,
                '5dbe3b7503790bd03e2879e7bb80384ecd82a4a1': 33,
                '1170150fbd861d5d4a3cbd1a1a3b9337ddf9be1e': 33,
                '19f894b54495cf3953e7afb098cfcd38c8e458e4': 33,
                '187bc79b50f93c88dbe1ad2b1685fe4571fbf1f1': 33,
                '89b96c890eeff7192031b5e816aaa65a468b4279': 55,
                '44eb52991a4295a0fdb808c707c47c071bb0df05': 33,
                'b755a2bc2d2705ef39e5f9bd6b2cdce4926f59f2': 19,
                '9d31e478e3624228fee4ff9fca2d9f64634a2ad0': 19,
                '17816e1496ff9e3ed37c1f82b049af34695f4ac1': 35,
                'b927dce600b84f5b277a30d1a1f61b9a5a4872fc': 55,
                'f5923f934482eb4bdbc562fb21204976ae13b1cd': 55,
                '361aafd5a1968787529007bfc3aa0443c4bf3baf': 35,
                '92c12031cfcc2105ed4ce95ddf0b4e28ab50734f': 55,
                '395aadb51939063335430adbeee75bf7e83b609d': 55,
                '673bf41b7c78708c2867176fe7c10277c10627b5': 55,
                'a78efb0f10eb16fd54062fe4e10c84e2bd2e0424': 55,
                '48f5582844cbb086e27ec6acb5dfb37a907a192e': 55,
                'efd52a03febd99a2a7ba2994930ce4a20ddbe5bf': 55,
                '37357546a8050b4852c0bac9887bef40e8a3846f': 35,
                'e215ec3f17c9d962bed61b3e9927120e530dcd3f': 55,
                '00aa2d712d66fc256782768e340ff28787fc7cc9': 55,
                'e09475aa63b51c8345cc2d886dac90ce966b7678': 55,
                'c7617c67bf312f4eec41226439cb3cdf17269e15': 55,
                '4cd38e7e80feee361b13b3f19c04eb2471b893ca': 23,
                'df1df9d48a98c4c2562dbbb4d9db3402d1b921b7': 44,
                '3806363c8c0f0731a5baa411bd8b0370f2b3b697': 48,
                'a522687ac83032e9c0778e734164cd410ba7f62e': 55,
                'c4125f9a5feb06715347d1f8b90fcb33aa656d2a': 55,
                '77dfdca1abb68b9e6398fe1e0b34e7fdf3304ee2': 6,
                '83848b1c5f09d86d7f927edfba003f7a5ce323d7': 44,
                'cb51bc02b648cd957f2b65d353959296609dd971': 16,
                'aed92e6dd879583565fcdf64f6670e3552123a6b': 55,
                '8fbd1b66f2145df180cd26bdd428f113d92b7243': 35,
                'f697cc619182c77550c88128bb4530ffd003b276': 18
              };
              document.querySelectorAll('div.flex > p:first-child').forEach(p => {
                const id = sha1(p.innerHTML);
                if (id2maxwidth.hasOwnProperty(id)) {
                  p.style.maxWidth = id2maxwidth[id] + 'rem';
                } /*else {*/
                  const div = p.parentElement;
                  const d = document.createElement("div");
                  d.setAttribute('class', 'temp');
                  d.innerHTML = id;
                  div.appendChild(d);
                /*}*/
              });

              const mergeList = [
                '4102b0dda9b55694454996b567767d7f61fa1a6e',
                '4395d0b684694c5dc6a60e14e0a4a58a32b36d50',
                '277cdf703990c741c609fe277a64a5fc4ec642a0',
                '4988677d8afe3f1314c60610c62a47a36ddd42f4',
                '5eb2b435ac600b2b7ad459d684f67ddd8cf28e95',
                'ebc666f95ac8a1311123e62bcaf239d0cdac3673',
                'b75e2fbbc0096649da87c261ade7fdb2118052c7',
                '1d45526f375a68f2cfa6382f52a5729d88a6b45c',
                '52130842f97581aebfebe5c414976f8452b616d8',
                'c8900d00443d4564c91dce2c93857c895d39b0dc',
                '2a8f738b46b596da871c3d7df303f503d2546b87',
                '1a180a8c046f0d0c0044ad9ee4371dbe7267a835',
                '0393dbc5c16426fba5fe217e8870e15364ddd370',
                '4fc49613736421c5526df5f47051346dc40ed425',
                'a8fbd244f589f59b681980ffa3d361bec5494ca8',
                '7be73d535cee52e760cb09158d54a9c40501ef5a',
                'eaf2f7c15224510ed216b37e9c963d7fbafa5bb8',
                'ebb18a04594ad2d5aa980f3e64451c0bc7fd58cf',
                '076764701187bc385c81b77bd455386120fba5b2',
                'c4219f8646c11f95f7643079c5db0d7253ac476d',
                'ff1061a460a9092bd3a8b0a3939cf30ff200048e',
                '2dea28a07e2f88625f896f5b82ed9c6a1a17fcbd',
                'e8b42a9e78ebee0df3028661987712717322f4ee',
                'bb25f62c25919ddab308e58c67b0bee399ee9e2f',
                '46718dd166f68dd58452975f7587fcf5f280fadd',
                'fbfa9d25618e6d59b4e83734471eb36606e903dd',
                '8987deaa0fa27a3ef734be96a7a88f6569d1e0bb',
                'fee7dd47f90f148ac33f6372f3ca867d9dff3200',
                'e9593269b9098b7dee86391e5b64a96fa04583c8',
                'db0ff9aaa2c79a505cf250d20417baedb1b97275',
                '02eec1d99a9ce3e9c624b9e6471fe3b7007bc292',
                '53b09800a35f8097b54986257d316adfbf115202',
                'fea3e45dbe0875c7d2761d7381798f54174b6c34',
                '25dc6116fc813a5e25009e616a7c17a630d71214',
                'c017539d7e4355ca355a64e67b4f6bd367c9e9e7',
                '2e71a91dad7aaa811d6cc17ee7ba17337b955121',
                '95f7643efe19db6a71e6e1c7fa9dbea62f5fc98c',
                'c15e8786c0c34d9ad16a04f9f0237d606903c909',
                '8f0f384c2bbda3d4c6f77b64209a9894280ce1e5',
                'e77ddc0e92368a90f646b9695e00c062a8c76e80',
                '8fbd1b66f2145df180cd26bdd428f113d92b7243',
                '8c8d02e875c6fa9649d8429f4a4d9699a0ab39f5',
                '35c456b5717ed84b27282a8004b3dff87d4da566',
                '60eac59b617edb1c60395e8b03b0f6cd98c27710',
                '7c139ef1582d73025108da5339db62e60f097ab3',
                'f697cc619182c77550c88128bb4530ffd003b276',
                'baafd0efcf82a6a013bde61d58526850ea1b47d4',
                '80a9a52d20398bf0ed25c666f44f53e0f1c515f9',
                '9b29fb058df43be39f859b4562ecde5793bda4aa',
                'aebd8c163a2994b2ade201258ad18f72c306a5c0',
                '4a7cdd2698992b13b2e34ff69636b82f39df7115',
                '760525d635ce366022a33844f7c4715ca220f4ef',
                '92366c80b431cd3ade77aa3a20e0423e76f56d94',
                '14ef79190cf7f54f6424a3a59914c53af28aef8a',
                'a6c30aa21b092883b4ef4801ff76e184b88a308d',
                '8799cac805dea2ed0194926a342ed4c90c902d65',
                'db0bd6823de0b7b6ab68679701666cc887e984ef',
                '100ccf93c8e3788781e79c3bf1cb911025b5b76f',
                '1d1b9c1d7e5a6e9412addda43d1b42bbfea133a1',
                'a9217803d4cde3ce76eddc553c84b0cf03d5c1e9',
                '306308242639b81e212c01b5c342d665d9565e33',
                '22f32f35e70d03578608645637c6fb91ff696031',
                '9c4d96350dfb80956f28b966214aab1b061052be',
                '968ff6e290f6cbe628ccf8cd329a23fe9fc50d0d',
                '64ee6eb62663f018504d87eab8287d65c242a919',
                '809ea06232ecf1f6527448365e3b41392c2d0af2',
                '13866edc6a975ea5f7d8a5e7540b867fd9c833dd',
                'aba13b90d23c791b3105e952b2ee982444a019b0',
                'c935fffed39237c829bcab1abdf489a6b1b3c0aa',
                '3165612e15dd81d081ad2ea3bdee303a0e8cef7a', // the innerText is: `Here is the output:`
                '98309907d947ce2d763414f36df6723ec3b202a0',
                '49968fdb9104c113dfc2bbdf669ebb2c24f9283a',
                '023f32299a56c55440a949e7dc2127cf3a04142c',
                'dcd0d9a3b1901a5ad525534234b736da748626ae',
                '4a310480afbd94666ab49c8e64877a50a5b97a60',
                '008c5ddcc9f5e75ca49dd437f1a92547df55edd2',
                'b74092ee0df3ba07fe0359c2fc212e1989374f34',
                '67cc10039fc91277812946577e58f67982792ca0',
                '860537f04c649b35d5f4b5e9d0386a36066753b8',
                '5af76eeb8a711f566c69c24bc2e3d49c23e14fc6',
                '3aa3550ae7537e156c24625ed232ab1d452a010e',
                '129c218da5f645ff93a76e232aa708a9c2b19dbd',
                '15bd2b13b05713aebae8438ba476411babd6eded',
                'e64962b7a693280832eaec7683f048330d4c8ed4',
                '41be75fff86ff0be9532f6f1ddb42cbd6c62d9b1',
                '05f8084fdec6c3e4c760b1a846067a19e06f2dbf',
                'b03957c5fe088370d96d9be6d036c3c1ca37d20a',
                '4efd4012fce438e069e069fedf293499d83ce617',
                'a522687ac83032e9c0778e734164cd410ba7f62e',
                'c4125f9a5feb06715347d1f8b90fcb33aa656d2a',
                'a31c8752bf48a8908e4f1f2fa55600add80225ca',
                '9ece2015b67717f1b074d8ed0b083d90c3ec68e4',
                'dd45436b8bda0e1dd39789fb8bde875cf8caefa5',
                'fbc73aa4d7a68a1bbd127fd4aecaa9805b2cbeb7',
                'c90d15d755c6128b7d559d8588996d67eced2978',
                '9339e183bc9e866f77c0db4e7dd8e2ac7cbbf014',
                '788155d2eaeaa3fd83f730e37c79dc695e6df1ff',
                '13319c6e36c3d03576495658bc54292195915d7f',
                '35f918c37be1e84da771d4a1c65d6fe756e2561e',
                '6ce2db1ece051f6ea14b57efadf33a1e5007c4f1',
                '31a68fde75725362d93191dec067ae026ae064b0',
                '6bbe3a7625d023fe1210a55ce22cf32786b86859',
                '8d7d1f3819d0858842e94c04345b252e9ace21c6',
                '66330ff16f46a032b2e6ddd742ac415744c3baa8',
                'a31bef06f7e5401192e175b13c280e8ab3977e94',
                'c1d7a47aa4225b555ff47b87af1f52cf44830175',
                '990950ef011cc47448fa85762e596d27fc15ff56',
                '1c4a507bb07bf7e7d22c8c5566863cd732735416',
                'e5ab9934dfc88feeec6aa398062e533207db5660',
                'a6700d99a9ed416d6f9f39526ef732f47a02d833',
                'a836acab40e21417f5b9f0b663411d5d83f6c9c9',
                '1d8e4e5d9900c4f2b20e9aa3e49131f62cfacb8c',
                'f2bbbade8894cef6c322fff28ffa383051bee5ad'
              ];
              document.querySelectorAll('div.flex > p:first-child').forEach(p => {
                const id = sha1(p.innerHTML);
                if (mergeList.indexOf(id) !== -1) {
                  const nextEl = p.parentElement.nextElementSibling;
                  // nextEl can be null because there are many p with the same content
                  if (nextEl !== null && nextEl.nodeName === 'DIV' && nextEl.classList.contains('flex')) {
                    p.parentElement.innerHTML += nextEl.innerHTML;
                    nextEl.parentElement.removeChild(nextEl);
                  }
                }
              });

              const removeFlex = [
                '10bf3a9ef866e9836df728cd6135ed3fa9726255'
              ];
              document.querySelectorAll('div.flex > p:first-child').forEach(p => {
                const id = sha1(p.innerHTML);
                if (removeFlex.indexOf(id) !== -1) {
                  p.parentElement.classList.remove('flex');
                }
              });

              document.head.parentElement.style.fontSize = '20px'; // TODO remove me
            </script>""");
    document
        .select("a[rel='noopener noreferrer']")
        .forEach(
            a -> {
              if (a.childNodeSize() == 0) a.remove();
            });
    document.select(".calibre5").forEach(el -> el.removeClass("calibre5"));
    document.select("p.body").forEach(p -> p.removeClass("body"));
    document // move the code annotation section to the right of the code
        .select("div.orm-ChapterReader-codeSnippetContainer + p.fm-code-annotation")
        .forEach(
            p -> {
              Element div = p.previousElementSibling();
              Element innerDiv = div.select("div.orm-ChapterReader-snippetButtonContainer").get(0);
              while (p != null) {
                Element nextP = p.nextElementSibling(); // can be null
                innerDiv.appendChild(p); // no need to remove p as it'll be re-parented
                if (nextP == null || !nextP.hasClass("fm-code-annotation") || !nextP.nameIs("p")) {
                  p = null;
                } else {
                  p = nextP;
                }
              }
            });
    document
        .select("p + div.orm-ChapterReader-codeSnippetContainer")
        .forEach(
            div -> {
              Element p = div.previousElementSibling();
              Element newDiv = document.createElement("div");
              newDiv.addClass("flex");
              p.before(newDiv);
              newDiv.appendChild(p);
              newDiv.appendChild(div);
            });
    document
        .select(
            "div.flex > div.orm-ChapterReader-codeSnippetContainer > div.orm-ChapterReader-snippetButtonContainer")
        .forEach(
            div -> {
              Element codeSnippetContainer = div.parent();
              Element flexDiv = codeSnippetContainer.parent();
              String html = codeSnippetContainer.html();
              codeSnippetContainer.remove();
              flexDiv.append(html);
            });
    document
        .select("p.co-summary-head + ul + *")
        .forEach(
            el -> {
              el.addClass("clear-both");
              Element ul = el.previousElementSibling();
              ul.addClass("float-left");
              Element p = ul.previousElementSibling();
              p.addClass("float-left");
            });
    document
        .select("div.orm-ChapterReader-snippetButtonContainer")
        .forEach(
            div -> {
              if (div.childNodeSize() == 0) div.remove();
            });

    // build the TOC
    Element tocDiv = document.createElement("div").attr("id", "toc");
    tocDiv.append(
        """
        <label for="toc-chkbox" style="cursor: pointer">Table of Contents</label>
        <input type="checkbox" id="toc-chkbox" style="visibility: hidden">""");
    Element innerTocDiv = document.createElement("div");
    AtomicInteger ai = new AtomicInteger();
    document
        .select("h1, h2, h3")
        .forEach(
            h -> {
              String id = "_id" + ai.incrementAndGet();
              h.attr("id", id);
              String cssClass = "toc-3";
              if (h.nameIs("h1")) {
                cssClass = "toc-1";
              } else if (h.nameIs("h2")) {
                cssClass = "toc-2";
              }
              innerTocDiv.append(
                  """
                  <a href="#%s" class="%s">%s</a>"""
                      .formatted(id, cssClass, h.text()));
            });
    tocDiv.appendChild(innerTocDiv);
    document.body().appendChild(tocDiv);

    // insert the pre back and write to file
    Utils.insertPreBackThenWriteToFile(document, tuple._2(), PATH_HTML);
  }
}
