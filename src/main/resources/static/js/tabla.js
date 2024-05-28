"use strict";

// Global variables
var params = null;  // Parameters
var colsEdi = null;
var newColHtml = `
    <div class="btn-group pull-right">
        <button id="bEdit" type="button" class="btn btn-sm btn-default" onclick="rowEdit(this);">
            <span class="glyphicon glyphicon-pencil"></span>
        </button>
        <button id="bElim" type="button" class="btn btn-sm btn-default" onclick="rowElim(this);">
            <span class="glyphicon glyphicon-trash"></span>
        </button>
        <button id="bAcep" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowAcep(this);">
            <span class="glyphicon glyphicon-ok"></span>
        </button>
        <button id="bCanc" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="rowCancel(this);">
            <span class="glyphicon glyphicon-remove"></span>
        </button>
    </div>`;
var colEdicHtml = `<td name="buttons">${newColHtml}</td>`;

function setEditable(options) {
    var defaults = {
        columnsEd: null,  // Index to editable columns. If null all td editables. Ex.: "1,2,3,4,5"
        addButton: null,  // DOM object of "Add" button
        onEdit: function () {},  // Called after edition
        onBeforeDelete: function () {},  // Called before deletion
        onDelete: function () {},  // Called after deletion
        onAdd: function () {}  // Called when added a new row
    };
    params = Object.assign(defaults, options);
    var table = document.getElementById("table-list");
    var headerRow = table.querySelector('thead tr');
    headerRow.insertAdjacentHTML('beforeend', '<td name="buttons"><span class="glyphicon glyphicon-thumbs-up"></span></td>');
    var bodyRows = table.querySelectorAll('tbody tr');
    bodyRows.forEach(row => row.insertAdjacentHTML('beforeend', colEdicHtml));

    if (params.addButton !== null) {
        params.addButton.addEventListener('click', function () {
            rowAddNew(table.id);
        });
    }
    if (params.columnsEd !== null) {
        colsEdi = params.columnsEd.split(',');
    }
}

function iterarCamposEdit(cols, tarea) {
    var n = 0;
    cols.forEach(function (col) {
        n++;
        if (col.getAttribute('name') === 'buttons') return;
        if (!esEditable(n - 1)) return;
        tarea(col);
    });

    function esEditable(idx) {
        if (colsEdi == null) {
            return true;
        } else {
            for (var i = 0; i < colsEdi.length; i++) {
                if (idx == colsEdi[i]) return true;
            }
            return false;
        }
    }
}

function fijModoNormal(but) {
    var buttons = but.parentElement;
    buttons.querySelector('#bAcep').style.display = 'none';
    buttons.querySelector('#bCanc').style.display = 'none';
    buttons.querySelector('#bEdit').style.display = 'inline';
    buttons.querySelector('#bElim').style.display = 'inline';
    var row = but.closest('tr');
    row.id = '';
}

function fijModoEdit(but) {
    var buttons = but.parentElement;
    buttons.querySelector('#bAcep').style.display = 'inline';
    buttons.querySelector('#bCanc').style.display = 'inline';
    buttons.querySelector('#bEdit').style.display = 'none';
    buttons.querySelector('#bElim').style.display = 'none';
    var row = but.closest('tr');
    row.id = 'editing';
}

function modoEdicion(row) {
    return row.id === 'editing';
}

function rowAcep(but) {
    var row = but.closest('tr');
    var cols = row.querySelectorAll('td');
    if (!modoEdicion(row)) return;
    iterarCamposEdit(cols, function (td) {
        var input = td.querySelector('input');
        if (input) {
            var cont = input.value;
            td.innerHTML = cont;
        }
    });
    fijModoNormal(but);
    params.onEdit(row);
}

function rowCancel(but) {
    var row = but.closest('tr');
    var cols = row.querySelectorAll('td');
    if (!modoEdicion(row)) return;
    iterarCamposEdit(cols, function (td) {
        var div = td.querySelector('div');
        if (div) {
            var cont = div.innerHTML;
            td.innerHTML = cont;
        }
    });
    fijModoNormal(but);
}

function rowEdit(but) {
    var row = but.closest('tr');
    var cols = row.querySelectorAll('td');
    if (modoEdicion(row)) return;
    iterarCamposEdit(cols, function (td) {
        var cont = td.innerHTML;
        var div = `<div style="display: none;">${cont}</div>`;
        var input = `<input class="form-control input-sm" value="${cont}">`;
        td.innerHTML = div + input;
    });
    fijModoEdit(but);
}

function rowElim(but) {
    var row = but.closest('tr');
    params.onBeforeDelete(row);
    row.remove();
    params.onDelete();
}

function rowAddNew(tabId) {
    var table = document.getElementById(tabId);
    var body = table.querySelector('tbody');
    var filas = body.querySelectorAll('tr');
    if (filas.length == 0) {
        var headerRow = table.querySelector('thead tr');
        var cols = headerRow.querySelectorAll('th');
        var htmlDat = '';
        cols.forEach(function (col) {
            if (col.getAttribute('name') == 'buttons') {
                htmlDat += colEdicHtml;
            } else {
                htmlDat += '<td></td>';
            }
        });
        body.insertAdjacentHTML('beforeend', `<tr>${htmlDat}</tr>`);
    } else {
        var lastRow = body.querySelector('tr:last-child');
        var newRow = lastRow.cloneNode(true);
        var cols = newRow.querySelectorAll('td');
        cols.forEach(function (col) {
            if (col.getAttribute('name') !== 'buttons') {
                col.innerHTML = '';
            }
        });
        body.appendChild(newRow);
    }
    params.onAdd();
}

function tableToCSV(tabId, separator) {
    var table = document.getElementById(tabId);
    var bodyRows = table.querySelectorAll('tbody tr');
    var csvContent = '';
    bodyRows.forEach(function (row) {
        if (modoEdicion(row)) {
            row.querySelector('#bAcep').click();
        }
        var cols = row.querySelectorAll('td');
        var rowData = '';
        cols.forEach(function (col) {
            if (col.getAttribute('name') !== 'buttons') {
                rowData += col.innerHTML + separator;
            }
        });
        if (rowData !== '') {
            rowData = rowData.slice(0, -separator.length);
        }
        csvContent += rowData + '\n';
    });
    return csvContent;
}

document.addEventListener('DOMContentLoaded', function () {
    var table = document.getElementById('table-list');
    setEditable({
        addButton: document.getElementById('add')
    });
});
