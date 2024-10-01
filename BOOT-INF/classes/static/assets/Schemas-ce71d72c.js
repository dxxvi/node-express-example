import{H as Ee,j as e,a1 as O,M as we,J as ke,s as g,e as w,O as se,k as Le,bg as ae,m as _,A as H,bh as A,bi as re,ax as ie,ak as B,ar as oe,d as D,bj as Oe,bk as Ae,bl as F,bm as $e,P as N,bn as ne,a8 as Pe,T as ce,bo as J,r as $,aA as le,bp as de,bq as he,br as me,bs as ue,bt as pe,bu as K,bv as G,bw as xe,bx as be,by as z,B as W,bz as Ve,bA as Re,at as Fe,aE as ge,i as v,aK as E,bB as De,al as U,ap as fe,ao as je,$ as R,l as Ne,a2 as q,a3 as I,a0 as L,bC as ye,bD as Me,am as qe,bE as Y,aO as Ie,t as _e,v as P,z as He,bF as Be,bG as ze}from"./index-77be7e32.js";import{a as m,d as We}from"./ace-fff8bc0c.js";import{A as Se}from"./ActionButton-1a464988.js";import{a as k,u as j}from"./redux-e9568735.js";import{C as Je,A as Ke}from"./ControlPanel.styled-f3142500.js";import{S as Ge}from"./Search-4d714508.js";import{L as Ue}from"./LinkCell-39711c54.js";import{u as Ye}from"./usePermission-54f4c8f1.js";import{T as Qe}from"./theme-textmate-f078d251.js";import{E as ve}from"./EditorViewer-d572ceef.js";import"./yupExtended-6e508a6b.js";import{E as Q}from"./Editor-8a81d97c.js";const Xe=({message:t=ke(),permission:a,placement:r="bottom",disabled:i,...s})=>{const c=!Ye(a.resource,a.action,a.value),{x:l,y:x,reference:o,floating:u,strategy:b,open:p}=Ee(c,r);return e.jsxs(e.Fragment,{children:[e.jsx("div",{ref:o,children:e.jsx(O,{...s,disabled:i||c})}),p&&e.jsx(we,{ref:u,style:{position:b,top:x??0,left:l??0,width:"max-content"},children:t})]})},Ze=g.div`
  display: flex;
  gap: 5px;
  align-items: center;
  & > div {
    color: ${({theme:t})=>t.select.label};
  }
`,et=()=>{const{clusterName:t}=w(),a=k(),[r]=se(),i=Le(),[s,n]=m.useState(),[c,l]=m.useState(!1);m.useEffect(()=>{(async()=>{l(!0);try{const{compatibility:u}=await A.getGlobalSchemaCompatibilityLevel({clusterName:t});n(u)}catch{}l(!1)})()},[t]);const x=o=>{const u=o;i(e.jsxs(e.Fragment,{children:["Are you sure you want to update the global compatibility level and set it to ",e.jsx("b",{children:u}),"? This may affect the compatibility levels of the schemas."]}),async()=>{try{await A.updateGlobalSchemaCompatibilityLevel({clusterName:t,compatibilityLevel:{compatibility:u}}),n(u),a(re({clusterName:t,page:Number(r.get("page")||1),perPage:Number(r.get("perPage")||ie),search:r.get("q")||""}))}catch(b){B(b)}})};return s?e.jsxs(Ze,{children:[e.jsx("div",{children:"Global Compatibility Level: "}),e.jsx(Xe,{selectSize:"M",defaultValue:s,minWidth:"200px",onChange:x,disabled:c,options:Object.keys(ae).map(o=>({value:o,label:o})),permission:{resource:_.SCHEMA,action:H.MODIFY_GLOBAL_COMPATIBILITY}})]}):null},tt=()=>{const t=k(),{isReadOnly:a}=m.useContext(oe),{clusterName:r}=w(),i=D(),s=j(Oe),n=j(Ae),c=j(o=>o.schemas.totalPages),[l]=se();m.useEffect(()=>(t(re({clusterName:r,page:Number(l.get("page")||1),perPage:Number(l.get("perPage")||ie),search:l.get("q")||""})),()=>{t(F($e))}),[r,t,l]);const x=m.useMemo(()=>[{header:"Subject",accessorKey:"subject",cell:({getValue:o})=>e.jsx(Ue,{value:`${o()}`,to:encodeURIComponent(`${o()}`)})},{header:"Id",accessorKey:"id"},{header:"Type",accessorKey:"schemaType"},{header:"Version",accessorKey:"version"},{header:"Compatibility",accessorKey:"compatibilityLevel"}],[]);return e.jsxs(e.Fragment,{children:[e.jsx(N,{text:"Schema Registry",children:!a&&e.jsxs(e.Fragment,{children:[e.jsx(et,{}),e.jsxs(Se,{buttonSize:"M",buttonType:"primary",to:ne,permission:{resource:_.SCHEMA,action:H.CREATE},children:[e.jsx(Pe,{})," Create Schema"]})]})}),e.jsx(Je,{hasInput:!0,children:e.jsx(Ge,{placeholder:"Search by Schema Name"})}),n?e.jsx(ce,{columns:x,data:s,pageCount:c,emptyMessage:"No schemas found",onRowClick:o=>i(J(r,o.original.subject)),serverSideProcessing:!0}):e.jsx($,{})]})},st=g.div`
  width: 100%;
  background-color: ${({theme:t})=>t.layout.stuffColor};
  padding: 16px;
  display: flex;
  justify-content: center;
  align-items: stretch;
  gap: 2px;
  max-height: 700px;

  & > * {
    background-color: ${({theme:t})=>t.default.backgroundColor};
    padding: 24px;
    overflow-y: scroll;
  }

  & > div:first-child {
    border-radius: 8px 0 0 8px;
    flex-grow: 2;
  }

  & > div:last-child {
    border-radius: 0 8px 8px 0;
    flex-grow: 1;

    & > div {
      display: flex;
      gap: 16px;
      padding-bottom: 16px;
    }

    p {
      color: ${({theme:t})=>t.schema.backgroundColor.p};
    }
  }
`,V=g(t=>e.jsx(le,{level:4,...t}))`
  color: ${({theme:t})=>t.lastestVersionItem.metaDataLabel.color};
  width: 110px;
`,at=({schema:{id:t,subject:a,schema:r,compatibilityLevel:i,version:s,schemaType:n}})=>e.jsxs(st,{children:[e.jsxs("div",{children:[e.jsx(le,{level:3,children:"Actual version"}),e.jsx(ve,{data:r,schemaType:n,maxLines:28})]}),e.jsxs("div",{children:[e.jsxs("div",{children:[e.jsx(V,{children:"Latest version"}),e.jsx("p",{children:s})]}),e.jsxs("div",{children:[e.jsx(V,{children:"ID"}),e.jsx("p",{children:t})]}),e.jsxs("div",{children:[e.jsx(V,{children:"Type"}),e.jsx("p",{children:n})]}),e.jsxs("div",{children:[e.jsx(V,{children:"Subject"}),e.jsx("p",{children:a})]}),e.jsxs("div",{children:[e.jsx(V,{children:"Compatibility"}),e.jsx("p",{children:i})]})]})]}),rt=({row:t})=>{var a,r;return e.jsx(ve,{data:(a=t==null?void 0:t.original)==null?void 0:a.schema,schemaType:(r=t==null?void 0:t.original)==null?void 0:r.schemaType})},it=()=>{var p,y;const t=D(),a=k(),{isReadOnly:r}=m.useContext(oe),{clusterName:i,subject:s}=w();m.useEffect(()=>(a(de({clusterName:i,subject:s})),()=>{a(F(he))}),[i,a,s]),m.useEffect(()=>(a(me({clusterName:i,subject:s})),()=>{a(F(ue))}),[i,a,s]);const n=j(f=>pe(f)),c=j(K),l=j(G),x=j(xe),o=j(be),u=m.useMemo(()=>[{header:"Version",accessorKey:"version"},{header:"ID",accessorKey:"id"},{header:"Type",accessorKey:"schemaType"}],[]),b=async()=>{try{await A.deleteSchema({clusterName:i,subject:s}),t("../")}catch(f){B(f)}};return x&&t("/404"),!l||!c?e.jsx($,{}):e.jsxs(e.Fragment,{children:[e.jsx(N,{text:c.subject,backText:"Schema Registry",backTo:z(i),children:!r&&e.jsxs(e.Fragment,{children:[e.jsx(W,{buttonSize:"M",buttonType:"primary",to:{pathname:Ve,search:`leftVersion=${(p=n[0])==null?void 0:p.version}&rightVersion=${(y=n[0])==null?void 0:y.version}`},children:"Compare Versions"}),e.jsx(Se,{buttonSize:"M",buttonType:"primary",to:Re,permission:{resource:_.SCHEMA,action:H.EDIT,value:s},children:"Edit Schema"}),e.jsx(Fe,{children:e.jsx(Ke,{confirm:e.jsxs(e.Fragment,{children:["Are you sure want to remove ",e.jsx("b",{children:s})," schema?"]}),onClick:b,danger:!0,permission:{resource:_.SCHEMA,action:H.DELETE,value:s},children:"Remove schema"})})]})}),e.jsx(at,{schema:c}),e.jsx(Qe,{children:"Old versions"}),o?e.jsx(ce,{columns:u,data:n,getRowCanExpand:()=>!0,renderSubComponent:rt,enableSorting:!0}):e.jsx($,{})]})},ot=g.textarea(({theme:{textArea:t}})=>ge`
    border: 1px ${t.borderColor.normal} solid;
    border-radius: 4px;
    width: 100%;
    padding: 12px;
    padding-top: 6px;
    color: ${({theme:a})=>a.default.color.normal};
    background-color: ${({theme:a})=>a.schema.backgroundColor.textarea};
    &::placeholder {
      color: ${t.color.placeholder.normal};
      font-size: 14px;
    }
    &:hover {
      border-color: ${t.borderColor.hover};
    }
    &:focus {
      outline: none;
      border-color: ${t.borderColor.focus};
      &::placeholder {
        color: ${t.color.placeholder.normal};
      }
    }
    &:disabled {
      color: ${t.color.disabled};
      border-color: ${t.borderColor.disabled};
      cursor: not-allowed;
    }
    &:read-only {
      color: ${t.color.readOnly};
      border: none;
      background-color: ${t.backgroundColor.readOnly};
      &:focus {
        &::placeholder {
          color: ${t.color.placeholder.focus.readOnly};
        }
      }
      cursor: not-allowed;
    }
  `),nt=g.form`
  padding: 16px;
  padding-top: 0;
  display: flex;
  flex-direction: column;
  gap: 16px;
  width: 50%;

  & > button {
    align-self: flex-start;
  }

  & textarea {
    height: 200px;
  }
  & select {
    width: 30%;
  }
`,X=[{value:v.AVRO,label:"AVRO"},{value:v.JSON,label:"JSON"},{value:v.PROTOBUF,label:"PROTOBUF"}],ct=async({subject:t,schema:a,schemaType:r},i)=>A.createNewSchema({clusterName:i,newSchemaSubject:{subject:t,schema:a,schemaType:r}}),lt=E.object().shape({subject:E.string().required("Subject is required.").matches(De,"Only alphanumeric, _, -, and . allowed"),schema:E.string().required("Schema is required."),schemaType:E.string().required("Schema Type is required.")}),dt=()=>{const{clusterName:t}=w(),a=D(),r=k(),i=U({mode:"onChange",defaultValues:{schemaType:v.AVRO},resolver:fe(lt)}),{register:s,handleSubmit:n,control:c,formState:{isDirty:l,isSubmitting:x,errors:o,isValid:u}}=i,b=async({subject:p,schema:y,schemaType:f})=>{try{const T=await ct({subject:p,schema:y,schemaType:f},t);r(ye(T)),a(J(t,p))}catch(T){B(T)}};return e.jsxs(je,{...i,children:[e.jsx(N,{text:"Create",backText:"Schema Registry",backTo:z(t)}),e.jsxs(nt,{onSubmit:n(b),children:[e.jsxs("div",{children:[e.jsx(R,{children:"Subject *"}),e.jsx(Ne,{inputSize:"M",placeholder:"Schema Name",autoFocus:!0,name:"subject",autoComplete:"off",disabled:x}),e.jsx(q,{children:e.jsx(I,{errors:o,name:"subject"})})]}),e.jsxs("div",{children:[e.jsx(R,{children:"Schema *"}),e.jsx(ot,{...s("schema",{required:"Schema is required."}),disabled:x}),e.jsx(q,{children:e.jsx(I,{errors:o,name:"schema"})})]}),e.jsxs("div",{children:[e.jsx(R,{children:"Schema Type *"}),e.jsx(L,{control:c,name:"schemaType",defaultValue:X[0].value,render:({field:{name:p,onChange:y,value:f}})=>e.jsx(O,{selectSize:"M",name:p,value:f,onChange:y,minWidth:"100%",disabled:x,options:X})}),e.jsx(q,{children:e.jsx(I,{errors:o,name:"schemaType"})})]}),e.jsx(W,{buttonSize:"M",buttonType:"primary",type:"submit",disabled:!u||x||!l,children:"Submit"})]})]})},ht=g.div`
  padding: 16px;
  padding-top: 0;
  & > form {
    display: flex;
    flex-direction: column;
    gap: 16px;

    & > div:first-child {
      display: flex;
      gap: 16px;

      & > * {
        width: 20%;
      }
    }

    & > button:last-child {
      width: 72px;
      align-self: center;
    }
  }
`,mt=g.div`
  display: flex;
  gap: 16px;

  & > * {
    flex-grow: 1;
  }
`,Z=g.div(({theme:t})=>ge`
    border: 1px solid ${t.layout.stuffBorderColor};
    border-radius: 8px;
    margin-bottom: 16px;
    padding: 16px;
    & > h4 {
      font-weight: 500;
      font-size: 16px;
      line-height: 24px;
      padding-bottom: 16px;
      color: ${t.heading.h4};
    }
  `),ut=()=>{const t=D(),a=k(),{clusterName:r,subject:i}=w(),s=j(h=>K(h)),n=j(G),c=j(xe),l=m.useMemo(()=>(s==null?void 0:s.schemaType)===v.PROTOBUF?s==null?void 0:s.schema:JSON.stringify(JSON.parse((s==null?void 0:s.schema)||"{}"),null,"	"),[s]),o=U({mode:"onChange",resolver:fe((()=>E.object().shape({newSchema:(s==null?void 0:s.schemaType)===v.PROTOBUF?E.string().required():E.string().required().isJsonObject("Schema syntax is not valid")}))()),defaultValues:{schemaType:s==null?void 0:s.schemaType,compatibilityLevel:s==null?void 0:s.compatibilityLevel,newSchema:l}}),{formState:{isDirty:u,isSubmitting:b,dirtyFields:p,errors:y},control:f,handleSubmit:T}=o,M=async h=>{if(!!s)try{if(p.compatibilityLevel&&(await A.updateSchemaCompatibilityLevel({clusterName:r,subject:i,compatibilityLevel:{compatibility:h.compatibilityLevel}}),a(Me({...s,compatibilityLevel:h.compatibilityLevel}))),p.newSchema||p.schemaType){const d=await A.createNewSchema({clusterName:r,newSchemaSubject:{...s,schema:h.newSchema||s.schema,schemaType:h.schemaType||s.schemaType}});a(ye(d))}t(J(r,i))}catch(d){B(d)}};return c&&t("/404"),!n||!s?e.jsx($,{}):e.jsxs(je,{...o,children:[e.jsx(N,{text:`${i} Edit`,backText:"Schema Registry",backTo:z(r)}),e.jsx(ht,{children:e.jsxs("form",{onSubmit:T(M),children:[e.jsxs("div",{children:[e.jsxs("div",{children:[e.jsx(R,{children:"Type"}),e.jsx(L,{control:f,rules:{required:!0},name:"schemaType",render:({field:{name:h,onChange:d,value:S}})=>e.jsx(O,{name:h,value:S,onChange:d,minWidth:"100%",disabled:!0,options:Object.keys(v).map(C=>({value:C,label:C}))})})]}),e.jsxs("div",{children:[e.jsx(R,{children:"Compatibility level"}),e.jsx(L,{control:f,name:"compatibilityLevel",render:({field:{name:h,onChange:d,value:S}})=>e.jsx(O,{name:h,value:S,onChange:d,minWidth:"100%",disabled:b,options:Object.keys(ae).map(C=>({value:C,label:C}))})})]})]}),e.jsxs(mt,{children:[e.jsx("div",{children:e.jsxs(Z,{children:[e.jsx("h4",{children:"Latest schema"}),e.jsx(Q,{schemaType:s==null?void 0:s.schemaType,isFixedHeight:!0,readOnly:!0,height:"372px",value:l,name:"latestSchema",highlightActiveLine:!1})]})}),e.jsxs("div",{children:[e.jsxs(Z,{children:[e.jsx("h4",{children:"New schema"}),e.jsx(L,{control:f,name:"newSchema",render:({field:{name:h,onChange:d,value:S}})=>e.jsx(Q,{schemaType:s==null?void 0:s.schemaType,readOnly:b,defaultValue:S,name:h,onChange:d})})]}),e.jsx(q,{children:e.jsx(I,{errors:y,name:"newSchema"})}),e.jsx(W,{buttonType:"primary",buttonSize:"M",type:"submit",disabled:!u||b||!!y.newSchema,children:"Submit"})]})]})]})})]})},pt=()=>{const t=k(),{clusterName:a,subject:r}=w();m.useEffect(()=>(t(de({clusterName:a,subject:r})),()=>{t(F(he))}),[a,t,r]);const i=j(n=>K(n));return!j(G)||!i?e.jsx($,{}):e.jsx(ut,{})},Te=m.forwardRef((t,a)=>{const{isFixedHeight:r,schemaType:i,...s}=t,n=!r&&t.value&&t.value.length===2?Math.max(t.value[0].split(/\r\n|\r|\n/).length+1,t.value[1].split(/\r\n|\r|\n/).length+1)*16:500;return e.jsx("div",{children:e.jsx(We,{name:"diff-editor",ref:a,mode:i===v.JSON||i===v.AVRO?"json5":"protobuf",theme:"textmate",tabSize:2,width:"100%",height:`${n}px`,showPrintMargin:!1,maxLines:1/0,readOnly:!0,wrapEnabled:!0,...s})})});Te.displayName="DiffViewer";const xt=g.div`
  align-items: stretch;
  display: block;
  flex-basis: 0;
  flex-grow: 1;
  flex-shrink: 1;
  min-height: min-content;
  padding-top: 1.5rem !important;

  .ace_content {
    background-color: ${({theme:t})=>t.default.backgroundColor};
    color: ${({theme:t})=>t.default.color.normal};
  }
  .ace_gutter-cell {
    background-color: ${({theme:t})=>t.ksqlDb.query.editor.cell.backgroundColor};
  }
  .ace_gutter-layer {
    background-color: ${({theme:t})=>t.ksqlDb.query.editor.layer.backgroundColor};
    color: ${({theme:t})=>t.default.color.normal};
  }
  .ace_cursor {
    color: ${({theme:t})=>t.ksqlDb.query.editor.cursor};
  }

  .ace_print-margin {
    display: none;
  }
  .ace_variable {
    color: ${({theme:t})=>t.ksqlDb.query.editor.variable};
  }
  .ace_string {
    color: ${({theme:t})=>t.ksqlDb.query.editor.aceString};
  }
  .codeMarker {
    background-color: ${({theme:t})=>t.ksqlDb.query.editor.codeMarker};
    position: absolute;
    z-index: 2000;
  }
`,bt=g.div`
  animation: fadein 0.5s;
`,gt=g.div`
  flex-direction: column;
  margin-left: -0.75rem;
  margin-right: -0.75rem;
  margin-top: -0.75rem;
  box-shadow: none;
  padding: 1.25rem;
  &:last-child {
    margin-bottom: -0.75rem;
  }
`,ft=g.div`
  align-items: stretch;
  display: block;
  flex-basis: 0;
  flex-grow: 1;
  flex-shrink: 1;
  min-height: min-content;
  &:not(.is-child) {
    display: flex;
  }
`,ee=g.div`
  flex: none;
  width: 50%;
`,te=g.div`
  width: 0.625em;
`,jt=g(W)`
  margin: 10px 9px;
`,yt=({versions:t,areVersionsFetched:a})=>{const{clusterName:r,subject:i}=w(),s=D(),n=qe(),c=m.useMemo(()=>new URLSearchParams(n.search),[n]),[l,x]=m.useState(c.get("leftVersion")||""),[o,u]=m.useState(c.get("rightVersion")||""),b=k();m.useEffect(()=>(b(me({clusterName:r,subject:i})),()=>{b(F(ue))}),[r,i,b]);const p=(h,d)=>{var C;const S=((C=h.find(Ce=>Ce.version===d))==null?void 0:C.schema)||(h.length?h[0].schema:"");return S.trim().startsWith("{")?JSON.stringify(JSON.parse(S),null,"	"):S},y=h=>h[0].schemaType,f=U({mode:"onChange"}),{formState:{isSubmitting:T},control:M}=f;return e.jsxs(e.Fragment,{children:[e.jsx(N,{text:`${i} compare versions`,backText:"Schema Registry",backTo:z(r)}),e.jsx(jt,{buttonType:"secondary",buttonSize:"S",onClick:()=>s(-1),children:"Back"}),e.jsx(bt,{children:a?e.jsxs(gt,{children:[e.jsxs(ft,{children:[e.jsx(ee,{children:e.jsx(te,{children:e.jsx(L,{defaultValue:l,control:M,rules:{required:!0},name:"schemaType",render:({field:{name:h}})=>e.jsx(O,{id:"left-select",name:h,value:l===""?t[0].version:l,onChange:d=>{s(Y(r,i)),c.set("leftVersion",d.toString()),c.set("rightVersion",o===""?t[0].version:o),s({search:`?${c.toString()}`}),x(d.toString())},minWidth:"100%",disabled:T,options:t.map(d=>({value:d.version,label:`Version ${d.version}`}))})})})}),e.jsx(ee,{children:e.jsx(te,{children:e.jsx(L,{defaultValue:o,control:M,rules:{required:!0},name:"schemaType",render:({field:{name:h}})=>e.jsx(O,{id:"right-select",name:h,value:o===""?t[0].version:o,onChange:d=>{s(Y(r,i)),c.set("leftVersion",l===""?t[0].version:l),c.set("rightVersion",d.toString()),s({search:`?${c.toString()}`}),u(d.toString())},minWidth:"100%",disabled:T,options:t.map(d=>({value:d.version,label:`Version ${d.version}`}))})})})})]}),e.jsx(xt,{children:e.jsx(Te,{value:[p(t,l),p(t,o)],setOptions:{autoScrollEditorIntoView:!0},isFixedHeight:!1,schemaType:y(t)})})]}):e.jsx($,{})})]})},St=t=>({versions:pe(t),areVersionsFetched:be(t)}),vt=Ie(St)(yt),Ft=()=>e.jsxs(_e,{children:[e.jsx(P,{index:!0,element:e.jsx(tt,{})}),e.jsx(P,{path:ne,element:e.jsx(dt,{})}),e.jsx(P,{path:He.subject,element:e.jsx(it,{})}),e.jsx(P,{path:Be,element:e.jsx(pt,{})}),e.jsx(P,{path:ze,element:e.jsx(vt,{})})]});export{Ft as default};
