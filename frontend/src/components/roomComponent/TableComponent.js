import React, {useState} from "react";
import '../../styles/RoomStyle.css';
import ReactTable, {useTable} from "react-table";
import {useSortBy} from "react-table";


const TableComponent = () => {
    const [selected, setSelected] = useState('');

    const Select = (e) => {
        let a = e.target.value;
        setSelected(a);
        document.getElementById('rrrr').style.color = "black";

    }



    const data = React.useMemo(
        () => [
            {
                col1: 'Угадайка',
                col2: '27',
                col3: 'Школа',
                col4: 'нет'
            },
            {
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
                            <th {...column.getHeaderProps(column.getSortByToggleProps())} style={{borderBottom: 'solid 3px red', color: 'black', width: 250, height: 30}}>
                             {column.render('Header')}
                            <span>{column.isSorted ? column.isSortedDesc ? '🔽' : '🔼' : ''}</span>
                            </th>
                        ))}
                    </tr>
                ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                {rows.map(row => {
                    prepareRow(row)
                    return (
                        <tr {...row.getRowProps()} >
                            {row.cells.map(cell => {
                                return (
                                    <td
                                        {...cell.getCellProps()}
                                        style={{
                                            padding: '10px',
                                            border: 'solid 1px gray',
                                        }}

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