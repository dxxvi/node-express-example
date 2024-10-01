import{u as A,c9 as g,a as j,c as L,ai as P,s as o,e as k,Z as R,k as b,j as t,ca as d,cb as z,P as T,T as w,t as K,v as D}from"./index-77be7e32.js";import{a as p}from"./ace-fff8bc0c.js";import{D as v}from"./DeleteIcon-a831fece.js";function E(s){return A(["clusters",s,"acls"],()=>g.listAcls({clusterName:s}),{suspense:!1})}function I(s){const r=j();return L(a=>g.deleteAcl({clusterName:s,kafkaAcl:a}),{onSuccess:()=>{P({message:"ACL deleted"}),r.invalidateQueries(["clusters",s,"acls"])}})}function M(s){const r=I(s);return{deleteResource:async a=>r.mutateAsync(a),...r}}const m=o.div`
  text-transform: capitalize;
`,H=o.div`
  svg {
    cursor: pointer;
  }
`,i=o.div`
  width: fit-content;
  text-transform: capitalize;
  padding: 2px 8px;
  font-size: 12px;
  line-height: 16px;
  border-radius: 16px;
  color: ${({theme:s})=>s.tag.color};
  background-color: ${({theme:s,chipType:r})=>{switch(r){case"success":return s.tag.backgroundColor.green;case"danger":return s.tag.backgroundColor.red;case"secondary":return s.tag.backgroundColor.secondary;default:return s.tag.backgroundColor.gray}}};
`,Q=o.div`
  display: flex;
  align-items: center;

  ${i} {
    margin-left: 4px;
  }
`,S=()=>{const{clusterName:s}=k(),r=R(),{data:a}=E(s),{deleteResource:f}=M(s),x=b(!0),[l,u]=p.useState(""),y=e=>{e&&x("Are you sure want to delete this ACL record?",()=>f(e))},C=p.useMemo(()=>[{header:"Principal",accessorKey:"principal",size:257},{header:"Resource",accessorKey:"resourceType",cell:({getValue:e})=>t.jsx(m,{children:e().toLowerCase()}),size:145},{header:"Pattern",accessorKey:"resourceName",cell:({getValue:e,row:c})=>{let n;return c.original.namePatternType===d.PREFIXED&&(n="default"),c.original.namePatternType===d.LITERAL&&(n="secondary"),t.jsxs(Q,{children:[e(),n?t.jsx(i,{chipType:n,children:c.original.namePatternType.toLowerCase()}):null]})},size:257},{header:"Host",accessorKey:"host",size:257},{header:"Operation",accessorKey:"operation",cell:({getValue:e})=>t.jsx(m,{children:e().toLowerCase()}),size:121},{header:"Permission",accessorKey:"permission",cell:({getValue:e})=>t.jsx(i,{chipType:e()===z.ALLOW?"success":"danger",children:e().toLowerCase()}),size:111},{id:"delete",cell:({row:e})=>t.jsx(H,{onClick:()=>y(e.original),children:t.jsx(v,{fill:l===e.id?r.acl.table.deleteIcon:"transparent"})}),size:76}],[l]),h=e=>{e&&typeof e=="object"&&"id"in e&&u(e.id)};return t.jsxs(t.Fragment,{children:[t.jsx(T,{text:"Access Control List"}),t.jsx(w,{columns:C,data:a??[],emptyMessage:"No ACL items found",onRowHover:h,onMouseLeave:()=>u("")})]})},O=()=>t.jsx(K,{children:t.jsx(D,{index:!0,element:t.jsx(S,{})})});export{O as default};
