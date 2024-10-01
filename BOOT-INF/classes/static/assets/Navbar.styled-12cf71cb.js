import{s as r}from"./index-77be7e32.js";const t=r.nav`
  display: flex;
  border-bottom: 1px ${({theme:o})=>o.primaryTab.borderColor.nav} solid;
  height: ${({theme:o})=>o.primaryTab.height};
  & a {
    height: 40px;
    min-width: 96px;
    padding: 0 16px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: 500;
    font-size: 14px;
    white-space: nowrap;
    color: ${({theme:o})=>o.primaryTab.color.normal};
    border-bottom: 1px ${({theme:o})=>o.default.transparentColor} solid;
    &.is-active {
      border-bottom: 1px ${({theme:o})=>o.primaryTab.borderColor.active}
        solid;
      color: ${({theme:o})=>o.primaryTab.color.active};
    }
    &.is-disabled {
      color: ${o=>o.theme.primaryTab.color.disabled};
      border-bottom: 1px ${({theme:o})=>o.default.transparentColor};
      cursor: not-allowed;
    }
    &:hover:not(.is-active, .is-disabled) {
      border-bottom: 1px ${({theme:o})=>o.default.transparentColor} solid;
      color: ${({theme:o})=>o.primaryTab.color.hover};
    }
  }
`;export{t as N};
