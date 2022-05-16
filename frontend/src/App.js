/* eslint-disable no-unused-vars */
import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import LandingPage from './pages/landing/LangingPage';
import ReferenceBookPage from './pages/referencebook/ReferenceBookPage';
import ErrorPage from './pages/system/ErrorPage';
import TestChoicePage from './pages/test-choice/TestChoicePage';
import TestPage from './pages/test/TestPage';


function App() {
  return (
    <div className='App'>
      <main>
        <BrowserRouter>
          <Switch>
            <Route exact path='/' component={LandingPage} />
            <Route path='/refbook' component={ReferenceBookPage} />
            <Route path='/exam' component={LandingPage} />
            <Route path='/learn' component={LandingPage} />
            <Route path='/test/process' component={TestPage} />
            <Route path='/test' component={TestChoicePage} />
            <Route path='*' component={ErrorPage} />
          </Switch>
        </BrowserRouter>

      </main>
    </div>
  );
}
export default App;