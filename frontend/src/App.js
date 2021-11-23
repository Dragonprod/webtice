/* eslint-disable no-unused-vars */
import React from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import LandingPage from "./pages/LangingPage";
import ReferenceBookPage from "./pages/ReferenceBookPage";
import ErrorPage from "./pages/ErrorPage";

function App() {
  // TODO: Add components for exam and learn path
  return (
    <div className="App">
      <main>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" component={LandingPage} />
            <Route path="/refbook" component={ReferenceBookPage} />
            <Route path="/exam" component={LandingPage} />
            <Route path="/learn" component={LandingPage} />
            <Route path="*" component={ErrorPage}/>
          </Switch>
        </BrowserRouter>
      </main>
    </div>
  );
}

export default App;
