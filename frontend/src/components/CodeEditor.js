/* eslint-disable no-unused-vars */
import React, { PureComponent, useState } from "react";
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

const CodeEditor = () => (
  <LiveProvider code="<strong>Hello World!</strong>">
    <LiveEditor />
    <LiveError />
    <LivePreview />
  </LiveProvider>
);

// class CodeEditor extends PureComponent {
//   render() {
//     return (
//       <LiveProvider code="<strong>Hello World!</strong>">
//         <LiveEditor />
//         <LiveError />
//         <LivePreview />
//       </LiveProvider>
//     );
//   }
// }
export default CodeEditor;
