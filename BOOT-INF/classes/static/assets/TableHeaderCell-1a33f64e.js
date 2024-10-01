import{s as a,aE as i,j as c}from"./index-77be7e32.js";var s=(o=>(o.ASC="ASC",o.DESC="DESC",o))(s||{});const D=a.table`
  width: ${o=>o.isFullwidth?"100%":"auto"};

  & td {
    border-top: 1px ${({theme:o})=>o.table.td.borderTop} solid;
    font-size: 14px;
    font-weight: 400;
    padding: 8px 8px 8px 24px;
    color: ${({theme:o})=>o.table.td.color.normal};
    vertical-align: middle;
    max-width: 350px;
    word-wrap: break-word;
  }

  & tbody > tr {
    &:hover {
      background-color: ${({theme:o})=>o.table.tr.backgroundColor.hover};
    }
  }
`,g=i(({theme:{table:o}})=>`
    cursor: pointer;

    padding-right: 18px;
    position: relative;

    &::before,
    &::after {
      border: 4px solid transparent;
      content: '';
      display: block;
      height: 0;
      right: 5px;
      top: 50%;
      position: absolute;
    }

    &::before {
      border-bottom-color: ${o.th.color.normal};
      margin-top: -9px;
    }

    &::after {
      border-top-color: ${o.th.color.normal};
      margin-top: 1px;
    }

    &:hover {
      color: ${o.th.color.hover};
      &::before {
        border-bottom-color: ${o.th.color.hover};
      }
      &::after {
        border-top-color: ${o.th.color.hover};
      }
    }
  `),u=i(({theme:{table:o}})=>`
    color: ${o.th.color.active};

    &:before {
        border-bottom-color: ${o.th.color.active};
    }
  `),$=i(({theme:{table:o}})=>`
    color: ${o.th.color.active};

    &:after {
        border-top-color: ${o.th.color.active};
    }
  `),C=a.span(({isOrderable:o,isOrdered:l,sortOrder:t,theme:{table:e}})=>i`
    font-family: Inter, sans-serif;
    font-size: 12px;
    font-style: normal;
    font-weight: 400;
    line-height: 16px;
    letter-spacing: 0;
    text-align: left;
    display: inline-block;
    justify-content: start;
    align-items: center;
    background: ${e.th.backgroundColor.normal};
    cursor: default;
    color: ${e.th.color.normal};

    ${o&&g}

    ${o&&l&&t===s.ASC&&u}

    ${o&&l&&t===s.DESC&&$}
  `),v=a.span`
  margin-left: 8px;
  font-family: Inter, sans-serif;
  font-style: normal;
  font-weight: 400;
  line-height: 16px;
  letter-spacing: 0;
  text-align: left;
  background: ${({theme:o})=>o.table.th.backgroundColor.normal};
  font-size: 14px;
  color: ${({theme:o})=>o.table.th.previewColor.normal};
  cursor: pointer;
`,w=a.th`
  padding: 4px 0 4px 24px;
  border-bottom-width: 1px;
  vertical-align: middle;
  text-align: left;
`,T=o=>{const{title:l,previewText:t,onPreview:e,orderBy:p,sortOrder:h,orderValue:r,handleOrderBy:n,...x}=o,b=!!r&&r===p,d=!!(r&&n),f=d&&{isOrderable:d,sortOrder:h,onClick:()=>r&&n&&n(r),onKeyDown:m=>m.code==="Space"&&r&&n&&n(r),role:"button",tabIndex:0};return c.jsxs(w,{...x,children:[c.jsx(C,{isOrdered:b,...f,children:l}),t&&c.jsx(v,{onClick:e,onKeyDown:e,role:"button",tabIndex:0,children:t})]})};export{s as S,D as T,T as a};
