import{H as u,j as o,aG as x,b8 as g,J as h,s as A}from"./index-77be7e32.js";import{u as D}from"./usePermission-54f4c8f1.js";import"./ace-fff8bc0c.js";const I=({permission:t,message:s=h(),placement:a="left",children:n,disabled:r,...i})=>{const e=!D(t.resource,t.action,t.value),{x:l,y:c,reference:p,floating:d,strategy:f,open:m}=u(e,a);return o.jsxs(o.Fragment,{children:[o.jsx(x,{...i,disabled:r||e,ref:p,children:n}),m&&o.jsx(g,{ref:d,style:{position:f,top:c??0,left:l??0},children:s})]})},v=A.div`
  display: flex;
  align-items: center;
  padding: 0 16px;
  margin: 0 0 16px;
  width: 100%;
  gap: 16px;
  color: ${({theme:t})=>t.default.color.normal};
  & > *:first-child {
    width: ${t=>t.hasInput?"38%":"auto"};
  }
`;export{I as A,v as C};
