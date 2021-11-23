/* eslint-disable no-unused-vars */
import React, { PureComponent, useState, Component } from "react";
import { LiveProvider, LiveEditor, LiveError, LivePreview } from "react-live";

// export default function CodeEditor() {
//   return (
//     <LiveProvider code="<strong>Hello World!</strong>">
//       <LiveEditor />
//       <LiveError />
//       <LivePreview />
//     </LiveProvider>
//   );
// }

// const CodeEditor = () => {
//   return (
//     <LiveProvider code="<strong>Hello World!</strong>">
//       <LiveEditor />
//       <LiveError />
//       <LivePreview />
//     </LiveProvider>
//   );
// };

// const CodeEditor = () => (
//   <LiveProvider code="<strong>Hello World!</strong>">
//     <LiveEditor />
//     <LiveError />
//     <LivePreview />
//   </LiveProvider>
// );

const LiveEdit = ({ noInline, code }) => (
  <LiveProvider code={code} noInline={noInline}>

    <LiveEditor />
    <LiveError />
    <LivePreview />

  </LiveProvider>
);

const jsxExample = `
<h3>
  Hello World!
</h3>
`.trim();

class CodeEditor extends Component {

  render() {
    return (
      <LiveEdit noInline code={jsxExample} />
    )
  }
}
export default CodeEditor;