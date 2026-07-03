import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import NoticeService from "../services/NoticeService";

function EditNotice() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [notice, setNotice] = useState({
        noticeTitle: "",
        noticeContent: "",
        noticeDate: "",
        noticeCategory: ""
    });

    useEffect(() => {
        loadNotice();
    }, []);

    const loadNotice = async () => {
        try {
            const response = await NoticeService.getNoticeById(id);
            setNotice(response.data);
        } catch (error) {
            console.log(error);
        }
    };

    const handleChange = (e) => {
        setNotice({
            ...notice,
            [e.target.name]: e.target.value
        });
    };

    const updateNotice = async (e) => {
        e.preventDefault();

        await NoticeService.updateNotice(id, notice);

        navigate("/");
    };

    return (
        <div>

            <h2>Edit Notice</h2>

            <form onSubmit={updateNotice}>

                <input
                type="text"
                name="noticeTitle"
                value={notice.noticeTitle}
                onChange={handleChange}
                />

                <br /><br />

                <textarea
                name="noticeContent"
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
                value={notice.noticeCategory}
                onChange={handleChange}
                />

                <br /><br />

                <button type="submit">
                    Update Notice
                    </button>

                    </form>

                    </div>
    );
}

export default EditNotice;
