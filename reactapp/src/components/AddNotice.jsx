import React, { useState } from "react";
import NoticeService from "../services/NoticeService";

function AddNotice() {

    const [notice, setNotice] = useState({
        noticeTitle: "",
        noticeContent: "",
        noticeDate: "",
        noticeCategory: ""
    });

    const [message, setMessage] = useState("");

    const handleChange = (e) => {
        setNotice({
            ...notice,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await NoticeService.addNotice(notice);
            setMessage("Notice added successfully!");

            setNotice({
                noticeTitle: "",
                noticeContent: "",
                noticeDate: "",
                noticeCategory: ""
            });

        } catch (error) {
            setMessage("Failed to add notice.");
        }
    };

    return (
        <div>
            <h2>Add Notice</h2>

            {message && <p>{message}</p>}

            <form onSubmit={handleSubmit}>

                <input
                type="text"
                name="noticeTitle"
                placeholder="Notice Title"
                value={notice.noticeTitle}
                onChange={handleChange}
                />

                <br /><br />

                <textarea
                name="noticeContent"
                placeholder="Notice Content"
                value={notice.noticeContent}
                onChange={handleChange}
                />

                <br /><br />

                <input
                type="date"
                name="noticeDate"
                value={notice.noticeDate}
                onChange={handleChange}
                />

                <br /><br />

                <input
                type="text"
                name="noticeCategory"
                placeholder="Notice Category"
                value={notice.noticeCategory}
                onChange={handleChange}
                />

                <br /><br />

                <button type="submit">
                    Add Notice
                    </button>

                    </form>

                    </div>
    );
}

export default AddNotice;
