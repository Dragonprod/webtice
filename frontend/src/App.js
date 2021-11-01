import "./App.css";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import LandingPage from "./pages/LangingPage";
import ReferenceBookPage from "./pages/ReferenceBookPage";

function App() {
  return (
    <div className="App">
      <main>
        <BrowserRouter>
          <Switch>
            <Route exact path="/" component={LandingPage} />
            <Route path="/refbook" component={ReferenceBookPage} />
          </Switch>
        </BrowserRouter>
      </main>
    </div>
  );
}

export default App;
