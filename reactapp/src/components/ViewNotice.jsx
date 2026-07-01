import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import NoticeService from "../services/NoticeService";

function ViewNotice() {

    const [notices, setNotices] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        loadNotices();
    }, []);

    const loadNotices = async () => {
        try {
            const response = await NoticeService.getAllNotices();
            setNotices(response.data);
        } catch (error) {
            setNotices([]);
        }
    };

    const deleteNotice = async (id) => {
        await NoticeService.deleteNotice(id);
        loadNotices();
    };

    const editNotice = (id) => {
        navigate(`/edit/${id}`);
    };

    return (
        <div>
            <h2>View Notices</h2>

            {notices.length === 0 ? (
                <p>No notices available</p>
            ) : (
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Content</th>
                            <th>Date</th>
                            <th>Category</th>
                            <th>Actions</th>
                            </tr>
                            </thead>

                            <tbody>
                                {notices.map((notice) => (
                                    <tr key={notice.noticeId}>
                                        <td>{notice.noticeId}</td>
                                        <td>{notice.noticeTitle}</td>
                                        <td>{notice.noticeContent}</td>
                                        <td>{notice.noticeDate}</td>
                                        <td>{notice.noticeCategory}</td>

                                        <td>

                                            <button
                                            onClick={() =>
                                            editNotice(notice.noticeId)
                                            }
                                        >
                                            Edit
                                            </button>
                                            
                                            <button
                                            onClick={() =>
                                            deleteNotice(notice.noticeId)
}
>
    Delete
    </button>
    
    </td>
    
    </tr>
))}
</tbody>

</table>
)}

</div>
);
}

export default ViewNotice;
