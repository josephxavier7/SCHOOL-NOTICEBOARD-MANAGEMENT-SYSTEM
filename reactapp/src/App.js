import React from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

import AddNotice from "./components/AddNotice";
import ViewNotice from "./components/ViewNotice";
import EditNotice from "./components/EditNotice";

function App() {
    return (
        <BrowserRouter>

        <nav>
            <Link to="/">View Notices</Link> |{" "}
            <Link to="/add">Add Notice</Link>
            </nav>

            <Routes>

                <Route
                path="/"
                element={<ViewNotice />}
                />

                <Route
                path="/add"
                element={<AddNotice />}
                />

                <Route
                path="/edit/:id"
                element={<EditNotice />}
                />

                </Routes>

                </BrowserRouter>
    );
}

export default App;
