import React, {useState} from "react";
import '../../styles/RoomStyle.css';
import {useTable} from "react-table";
import {useSortBy} from "react-table";


const TableComponent = () => {
    const [selected, setSelected] = useState('1');



    const data = React.useMemo(
        () => [
            {
                id: 1,
                col1: 'Угадайка',
                col2: '27',
                col3: 'Школа',
                col4: 'нет'
            },
            {
                id: 2,
                col1: 'Алиас',
                col2: '30',
                col3: 'Животные',
                col4: 'да',
            },
            {
                id: 3,
                col1: 'Алиас',
                col2: '30',
                col3: 'Животные',
                col4: 'да',
            },
            {
                id: 4,
                col1: 'Алиас',
                col2: '30',
                col3: 'Животные',
                col4: 'да',
            },


        ],
        []
    )
    const columns = React.useMemo(
        () => [
            {
                Header: 'Название',
                accessor: 'col1',
            },
            {
                Header: 'Игроки',
                accessor: 'col2',
            },
            {
                Header: 'Словари',
                accessor: 'col3',
            },
            {
                Header: 'Пароль',
                accessor: 'col4',
            }
        ],
        []
    )

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = useTable({ columns, data }, useSortBy);


    return (
        <div className="table-component">
            <table {...getTableProps()} style={{ }}>
                <thead> {headerGroups.map(headerGroup => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                            <th {...column.getHeaderProps(column.getSortByToggleProps())} style={{borderBottom: 'solid 3px #B7F46E', color: 'black', width: 250, height: 30}}>
                             {column.render('Header')}
                            <span>{column.isSorted ? column.isSortedDesc ? '↓' : '↑' : ''}</span>
                            </th>
                        ))}
                    </tr>
                ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                {rows.map(row => {
                    prepareRow(row)
                    return (
                        <tr  {...row.getRowProps()} id = {row.id} >
                            {row.cells.map(cell => {
                                return (
                                    <td
                                        {...cell.getCellProps()}
                                        style={{
                                            padding: '10px',
                                            border: 'solid 1px #d1d1d1',
                                        }}
                                        onClick={() => {document.getElementById(selected).style.background = "#FFFFFF";
                                            document.getElementById(row.id).style.background = "#B7F46E";
                                            setSelected(row.id)}}
                                    >
                                        {cell.render('Cell')}

                                    </td>
                                )
                            })}
                        </tr>
                    )
                })}
                </tbody>
            </table>
        </div>
    );
}

export default TableComponent;