const { DataTypes } = require('sequelize');

module.exports = model;

function model(sequelize) {
    const attributes = {
        meter_id: {
            type: DataTypes.INTEGER,
            references: {
              model: 'Meters',
              key: 'id',
            },
            allowNull: true
        },
    
        value: { type: DataTypes.STRING, allowNull: false },
    };

    return sequelize.define('Value', attributes);
}