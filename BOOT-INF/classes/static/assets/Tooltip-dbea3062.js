import{s as r,E as f,F as m,G as u,j as t}from"./index-77be7e32.js";import{r as j}from"./ace-fff8bc0c.js";const v=r.div`
  max-width: 100%;
  max-height: 100%;
  background-color: ${({theme:e})=>e.tooltip.bg};
  color: ${({theme:e})=>e.tooltip.text};
  border-radius: 6px;
  padding: 5px;
  z-index: 1;
  white-space: pre-wrap;
`,y=r.div`
  display: flex;
  align-items: center;
`,b=({value:e,content:n,placement:a})=>{const[s,i]=j.useState(!1),{x:p,y:c,refs:o,strategy:l,context:x}=f({open:s,onOpenChange:i,placement:a}),d=m(x),{getReferenceProps:g,getFloatingProps:h}=u([d]);return t.jsxs(t.Fragment,{children:[t.jsx("div",{ref:o.setReference,...g(),children:t.jsx(y,{children:e})}),s&&t.jsx(v,{ref:o.setFloating,style:{position:l,top:c??0,left:p??0,width:"max-content"},...h(),children:n})]})};export{v as M,b as T};
